package com.violinstudio.scheduling.cqrs.students.queries;

import com.violinstudio.scheduling.domain.student.StudentContact;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class GetStudentContactDto {

    String id;
    String contact_type;
    String name;
    String email;
    String phone;

    public static GetStudentContactDto fromDomain(StudentContact s){

        String joinedName = s.getName().getFirstName() + " " + s.getName().getLastName();

        return new GetStudentContactDto(s.getId(), s.getContactType().getType(),
                joinedName, s.getEmail().getValue(), s.getPhone().getValue());
    }
}
