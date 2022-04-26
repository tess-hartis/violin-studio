package com.violinstudio.scheduling.cqrs.instructor;

import com.violinstudio.scheduling.domain.instructor.Instructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetInstructorNoDetailsDto {

    String id;
    String first_name;
    String last_name;

    public static GetInstructorNoDetailsDto fromDomain(Instructor i){
        return new GetInstructorNoDetailsDto(

                i.getId(),
                i.getName().getFirstName(),
                i.getName().getLastName()
        );
    }
}
