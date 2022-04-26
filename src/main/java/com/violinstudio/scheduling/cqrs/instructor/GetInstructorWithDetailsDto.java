package com.violinstudio.scheduling.cqrs.instructor;

import com.violinstudio.scheduling.cqrs.course.queries.GetCourseNoDetailsDto;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GetInstructorWithDetailsDto {

    String id;
    String first_name;
    String last_name;
    String date_added;
    List<GetInstructorContactDto> contact;
    List<GetCourseNoDetailsDto> courses;

    public static GetInstructorWithDetailsDto fromDomain(Instructor i){

        return new GetInstructorWithDetailsDto(

                i.getId(),
                i.getName().getFirstName(),
                i.getName().getLastName(),
                i.getDateAdded(),
                i.getContacts().stream().map(GetInstructorContactDto::fromDomain).collect(Collectors.toList()),
                i.getClasses().stream().map(GetCourseNoDetailsDto::fromDomain).collect(Collectors.toList())
        );

    }
}
