package com.violinstudio.scheduling.cqrs.students.commands;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.student.ContactType;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.domain.student.StudentContact;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.combine;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
public class PostPrimaryContactCommand {

    private final StudentsRepository studentsRepository;

    public Option<Validation<List<String>, StudentContact>> handle(PostPrimaryContactDto dto){

        var student = studentsRepository.findOne(dto.student_id);

        var contactType = student
                .map(s -> checkForDuplicatePrimaryContact(s, ContactType.unsafe("Primary")));

        var name = Name.validate(dto.first_name, dto.last_name);
        var email = Email.validate(dto.email);
        var phone = Phone.validate(dto.phone);
        Validation<String, String> id = valid(NanoIdUtils.randomNanoId());
        Validation<String, String> studentId = valid(dto.getStudent_id());

        return contactType.map(ct -> combine(id, ct, name, email, phone, studentId)
                .ap(StudentContact::new)
                .map(studentsRepository::addContact)
                .mapError(Value::toJavaList));

    }

    Validation<String, ContactType> checkForDuplicatePrimaryContact (Student s, ContactType ct){

        if (s.duplicatePrimaryContact())
            return invalid("Student already has a primary contact");

        return valid(ct);
    }
}
