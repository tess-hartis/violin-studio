package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseDto {

    String id;
    String course_type;
    String description;

    public static GetCourseDto fromDomain(Course c){
        return new GetCourseDto(c.getId(), c.getCourseType().getValue(), c.getDescription());
    }
}
