package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class PostStudentCmdHandler implements Command.Handler<PostStudentCmd, Validation<List<String>, Student>> {

    private final StudentsRepository studentsRepository;

    public Validation<List<String>, Student> handle(PostStudentCmd command){
        return command.toDomain()
                .map(studentsRepository::saveNew)
                .mapError(Value::toJavaList);
    }
}
