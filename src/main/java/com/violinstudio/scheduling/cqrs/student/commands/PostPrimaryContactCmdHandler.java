package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.student.ContactType;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.domain.student.StudentContact;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;
import static io.vavr.control.Validation.combine;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PostPrimaryContactCmdHandler implements
        Command.Handler<PostPrimaryContactCmd, Option<Validation<List<String>, StudentContact>>> {

    private final StudentsRepository studentsRepository;

    public Option<Validation<List<String>, StudentContact>> handle(PostPrimaryContactCmd command){

        var student = studentsRepository.findOne(command.student_id);

        var contactType = student
                .map(s -> checkForDuplicatePrimaryContact(s, ContactType.unsafe("Primary")));

        var name = Name.validate(command.first_name, command.last_name);
        var email = Email.validate(command.email);
        var phone = Phone.validate(command.phone);
        Validation<String, String> id = valid(NanoIdUtils.randomNanoId());
        Validation<String, String> studentId = valid(command.student_id);

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
