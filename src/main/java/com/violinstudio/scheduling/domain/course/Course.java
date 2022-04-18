package com.violinstudio.scheduling.domain.course;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.domain.student.Student;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Course {

    @NonNull String id;
    @NonNull @With
    CourseType courseType;
    @NonNull @With String description;
    @NonNull @With
    StudentLimit studentLimit;

    List<CourseDetails> courseDetails = new ArrayList<>();
    List<Instructor> instructors = new ArrayList<>();
    List<Student> students = new ArrayList<>();

    public Course update(CourseType courseType, String description, StudentLimit studentLimit){
        return this.withCourseType(courseType).withDescription(description).withStudentLimit(studentLimit);
    }
}
