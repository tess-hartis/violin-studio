package com.violinstudio.scheduling.cqrs.course.queries;

import com.violinstudio.scheduling.domain.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllCoursesDto {

    String id;
    String course_type;
    String description;

    public static GetAllCoursesDto fromDomain(Course c){
        return new GetAllCoursesDto(c.getId(), c.getCourseType().getValue(), c.getDescription());
    }
}
