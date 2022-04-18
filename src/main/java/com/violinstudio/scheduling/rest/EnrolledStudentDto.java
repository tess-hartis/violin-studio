package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrolledStudentDto {

    String id;
    String name;

    public static EnrolledStudentDto fromDomain(Student s){
        return new EnrolledStudentDto(s.getId(),
                s.getStudentName().getFirstName() + " " + s.getStudentName().getLastName());
    }
}
