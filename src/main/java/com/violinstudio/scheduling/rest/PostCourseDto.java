package com.violinstudio.scheduling.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseType;
import com.violinstudio.scheduling.domain.course.StudentLimit;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PostCourseDto {

    String course_type;
    String description;
    Integer student_limit;

    public Validation<Seq<String>, Course> toDomain(){

        var ct = CourseType.validate(course_type);
        Validation<String, String> d = Validation.valid(description);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());
        var sl = StudentLimit.validate(student_limit);

        return Validation.combine(nanoid, ct, d, sl).ap(Course::new);
    }
}
