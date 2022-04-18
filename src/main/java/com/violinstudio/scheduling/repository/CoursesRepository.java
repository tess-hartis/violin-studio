package com.violinstudio.scheduling.repository;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseDetails;
import io.vavr.control.Option;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CoursesRepository {

    Course saveNew(Course course);
    List<Course> findAll();
    Option<Course> findOne(String id);
    Integer deleteOne(String id);
    Course update(Course student);
    CourseDetails addDetails(CourseDetails courseSchedule);
}
