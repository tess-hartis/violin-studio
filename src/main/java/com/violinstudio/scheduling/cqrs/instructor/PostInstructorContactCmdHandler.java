package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Address;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PostInstructorContactCmdHandler implements
        Command.Handler<PostInstructorContactCmd, Option<Validation<List<String>, InstructorContact>>> {

    private final InstructorsRepository instructorsRepository;

    @Override
    public Option<Validation<List<String>, InstructorContact>> handle(PostInstructorContactCmd command) {

        var instructor = instructorsRepository.findOne(command.instructorId);

        var address = Address.validate(command.street_address, command.city,
                command.state, command.zipcode);
        var email = Email.validate(command.email);
        var phone = Phone.validate(command.phone);
        Validation<String, String> id = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> instructorId = Validation.valid(command.instructorId);

        return instructor.map(i -> Validation.combine(id, email, phone, address, instructorId)
                .ap(InstructorContact::new)
                .map(instructorsRepository::addContact)
                .mapError(Value::toJavaList));
    }
}
