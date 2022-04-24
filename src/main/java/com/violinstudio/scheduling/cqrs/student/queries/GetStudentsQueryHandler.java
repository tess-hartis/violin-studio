package com.violinstudio.scheduling.cqrs.student.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class GetStudentsQueryHandler implements Command.Handler<GetStudentsQuery, List<Student>> {

    private final StudentsRepository studentsRepository;

    @Override
    public List<Student> handle(GetStudentsQuery getStudentsQuery) {
        return studentsRepository.findAll();
    }
}
