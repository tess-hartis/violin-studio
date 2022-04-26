package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.student.ContactType;
import com.violinstudio.scheduling.domain.student.StudentContact;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class PostSecondaryContactCmdHandler implements
        Command.Handler<PostSecondaryContactCmd, Option<Validation<List<String>, StudentContact>>> {

    private final StudentsRepository studentsRepository;

    public Option<Validation<List<String>, StudentContact>> handle(PostSecondaryContactCmd command){

        var student = studentsRepository.findOne(command.student_id);

        var n = Name.validate(command.first_name, command.last_name);
        var e = Email.validate(command.email);
        var p = Phone.validate(command.phone);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> studentId = Validation.valid(command.student_id);
        Validation<String, ContactType> ct = Validation.valid(ContactType.unsafe("Secondary"));

        return student.map(s -> Validation.combine(nanoid, ct, n, e, p, studentId)
                .ap(StudentContact::new)
                .map(studentsRepository::addContact)
                .mapError(Value::toJavaList));
    }
}
