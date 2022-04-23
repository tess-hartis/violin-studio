package com.violinstudio.scheduling.cqrs.students.commands;

import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PostStudentCommand {

    private final StudentsRepository studentsRepository;

    public Validation<List<String>, Student> handle(PostStudentDto dto){
        return dto.toDomain()
                .map(studentsRepository::saveNew)
                .mapError(Value::toJavaList);
    }
}
