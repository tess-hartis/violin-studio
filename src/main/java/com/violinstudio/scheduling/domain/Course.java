package com.violinstudio.scheduling.domain;
import lombok.*;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Course {

    @NonNull String id;
    @NonNull @With CourseType courseType;
    @NonNull @With String description;

    CourseDetails details;
    List<Instructor> instructors = new ArrayList<>();
    List<Student> students = new ArrayList<>();

    public Course update(CourseType courseType, String description){
        return this.withCourseType(courseType).withDescription(description);
    }

    public Course addDetails(String id, CourseType courseType, String description, CourseDetails courseDetails){
        return new Course(id, courseType, description, details);
    }

}
