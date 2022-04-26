package com.violinstudio.scheduling.cqrs.course.queries;

import com.violinstudio.scheduling.domain.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseNoDetailsDto {

    String id;
    String course_type;
    String description;

    public static GetCourseNoDetailsDto fromDomain(Course c){
        return new GetCourseNoDetailsDto(c.getId(), c.getCourseType().getValue(), c.getDescription());
    }
}
