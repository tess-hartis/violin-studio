package com.violinstudio.scheduling.cqrs.course.queries;

import com.violinstudio.scheduling.domain.course.Course;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GetCourseWithDetailsDto {

    String id;
    String course_type;
    String description;
    Integer student_limit;
    Integer student_openings;
    List<GetCourseDetailsDto> details;
    List<EnrolledStudentDto> enrolled;


    public static GetCourseWithDetailsDto fromDomain(Course c){

        var studentOpenings = c.getStudentLimit().getValue() - c.getStudents().size();
        return new GetCourseWithDetailsDto(c.getId(), c.getCourseType().getValue(), c.getDescription(),
                c.getStudentLimit().getValue(), studentOpenings,
                c.getCourseDetails().stream().map(GetCourseDetailsDto::fromDomain).collect(Collectors.toList()),
                c.getStudents().stream().map(EnrolledStudentDto::fromDomain).collect(Collectors.toList()));
    }
}
