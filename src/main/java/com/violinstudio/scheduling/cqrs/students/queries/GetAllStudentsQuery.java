package com.violinstudio.scheduling.cqrs.students.queries;

import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.StudentsRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class GetAllStudentsQuery {

    private final StudentsRepository studentsRepository;

    public List<Student> handle(){
        return studentsRepository.findAll();
    }
}
