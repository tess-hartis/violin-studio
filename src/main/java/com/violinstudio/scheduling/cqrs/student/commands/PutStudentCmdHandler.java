package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PutStudentCmdHandler implements Command.Handler<PutStudentCmd, Option<Validation<List<String>, Student>>> {

    private final StudentsRepository studentsRepository;

    public Option<Validation<List<String>, Student>> handle(PutStudentCmd command){

        var student = studentsRepository.findOne(command.studentId);
        return student.map(command::toDomain)
                .map(x -> x.map(studentsRepository::update)
                        .mapError(Value::toJavaList));
    }
}
