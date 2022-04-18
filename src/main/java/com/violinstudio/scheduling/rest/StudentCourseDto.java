package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentCourseDto {

    String id;
    String course_type;
    String description;

    public static StudentCourseDto fromDomain(Course c){
        return new StudentCourseDto(c.getId(), c.getCourseType().getValue(), c.getDescription());
    }


}
