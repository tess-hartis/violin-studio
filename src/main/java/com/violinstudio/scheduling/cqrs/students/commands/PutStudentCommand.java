package com.violinstudio.scheduling.cqrs.students.commands;

import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PutStudentCommand {

    private final StudentsRepository studentsRepository;

    public Option<Validation<List<String>, Student>> handle(PutStudentDto dto){

        var student = studentsRepository.findOne(dto.studentId);
        return student.map(dto::toDomain)
                .map(x -> x.map(studentsRepository::update)
                        .mapError(Value::toJavaList));
    }
}
