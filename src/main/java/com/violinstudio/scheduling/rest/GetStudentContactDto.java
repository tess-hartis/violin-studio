package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.Student;
import com.violinstudio.scheduling.domain.StudentContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
public class GetStudentContactDto {

    String id;
    Boolean primary_contact;
    String name;
    String email;
    String phone;

    public static GetStudentContactDto fromDomain(StudentContact s){

        String joinedName = s.getName().getFirstName() + " " + s.getName().getLastName();

        return new GetStudentContactDto(s.getId(), s.getPrimaryContact(),
                joinedName, s.getEmail().getValue(), s.getPhone().getValue());
    }
}
