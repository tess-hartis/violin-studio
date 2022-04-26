package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseType;
import com.violinstudio.scheduling.domain.course.StudentLimit;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
public class PutCourseCmd implements Command<Option<Validation<List<String>, Course>>> {

    String course_type;
    String description;
    Integer student_limit;
    String courseId;

    public Validation<Seq<String>, Course> toDomain(Course c){
        var t = CourseType.validate(course_type);
        Validation<String, String> d = Validation.valid(description);
        var sl = StudentLimit.validate(student_limit);

        return Validation.combine(t, d, sl).ap(c::update);
    }

    public void setCourseId(String id){
        this.courseId = id;
    }
}
