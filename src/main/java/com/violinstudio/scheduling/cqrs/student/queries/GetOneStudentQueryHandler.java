package com.violinstudio.scheduling.cqrs.student.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class GetOneStudentQueryHandler implements Command.Handler<GetOneStudentQuery, Option<Student>> {

    private final StudentsRepository studentsRepository;

    public Option<Student> handle(GetOneStudentQuery query) {
        return studentsRepository.findOne(query.id);
    }
}
