package com.violinstudio.scheduling.repository.instructor;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InstructorsRepository {

    Option<Instructor> saveNew(Instructor instructor);
    List<Instructor> findAll();
    Option<Instructor> findOne(String id);
    Integer deleteOne(String id);
    Option<Instructor> update(Instructor instructor);
    InstructorContact addContact(InstructorContact instructorContact);
    Instructor addCourse(Instructor instructor, Course course);
}