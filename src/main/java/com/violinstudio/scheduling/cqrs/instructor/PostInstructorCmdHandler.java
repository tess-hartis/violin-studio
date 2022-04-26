package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PostInstructorCmdHandler implements Command.Handler<PostInstructorCmd, Validation<List<String>, Instructor>> {

    private final InstructorsRepository instructorsRepository;

    @Override
    public Validation<List<String>, Instructor> handle(PostInstructorCmd command) {
        return command.toDomain()
                .map(instructorsRepository::saveNew)
                .mapError(Value::toJavaList);


    }
}
