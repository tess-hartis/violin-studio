package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.Course;
import com.violinstudio.scheduling.domain.CourseType;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PutCourseDto {

    String course_type;
    String description;

    public Validation<Seq<String>, Course> toDomain(Course c){
        var t = CourseType.validate(course_type);
        Validation<String, String> d = Validation.valid(description);

        return Validation.combine(t, d).ap(c::udpate);
    }
}
