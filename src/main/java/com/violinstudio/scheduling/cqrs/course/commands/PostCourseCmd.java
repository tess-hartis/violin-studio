package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseType;
import com.violinstudio.scheduling.domain.course.StudentLimit;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
public class PostCourseCmd implements Command<Validation<List<String>, Course>> {

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
