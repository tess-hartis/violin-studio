package com.violinstudio.scheduling.domain;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Course {

    @NonNull String id;
    @NonNull @With CourseType courseType;
    @NonNull @With String description;
    @NonNull @With StudentLimit studentLimit;

    List<CourseDetails> courseDetails = new ArrayList<>();
    List<Instructor> instructors = new ArrayList<>();
    List<Student> students = new ArrayList<>();

    public Course update(CourseType courseType, String description, StudentLimit studentLimit){
        return this.withCourseType(courseType).withDescription(description).withStudentLimit(studentLimit);
    }

}
