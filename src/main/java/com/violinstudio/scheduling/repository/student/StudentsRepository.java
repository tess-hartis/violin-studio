package com.violinstudio.scheduling.repository.student;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.domain.student.StudentContact;
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
    Student addCourse(Student student, Course course);

}
