package com.violinstudio.scheduling.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.Course;
import com.violinstudio.scheduling.domain.CourseType;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PostCourseDto {

    String course_type;
    String description;

    public Validation<Seq<String>, Course> toDomain(){

        var ct = CourseType.validate(course_type);
        Validation<String, String> d = Validation.valid(description);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());

        return Validation.combine(nanoid, ct, d).ap(Course::new);
    }
}
