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
public class PutInstructorCmdHandler implements Command.Handler<PutInstructorCmd,
        Option<Validation<List<String>, Option<Instructor>>>> {

    private final InstructorsRepository instructorsRepository;

    @Override
    public Option<Validation<List<String>, Option<Instructor>>> handle(PutInstructorCmd command) {

        var instructor = instructorsRepository.findOne(command.instructorId);
        return instructor.map(command::toDomain)
                .map(x -> x.map(instructorsRepository::update)
                        .mapError(Value::toJavaList));
    }
}
