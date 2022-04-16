package com.violinstudio.scheduling.repository;

import com.violinstudio.scheduling.domain.*;
import com.violinstudio.scheduling.rest.GetStudentDto;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentsRepository {

    Student saveNew(Student student);
    List<Student> findAll();
    Option<Student> findOne(String id);
    Integer deleteOne(String id);
    Student update(Student student);
    StudentContact addContact(StudentContact studentContact);

}
