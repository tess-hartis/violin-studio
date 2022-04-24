package com.violinstudio.scheduling.cqrs.student.queries;

import com.violinstudio.scheduling.cqrs.student.commands.StudentCourseDto;
import com.violinstudio.scheduling.domain.student.Student;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class GetStudentDto {

    String id;
    String first_name;
    String last_name;
    String birthday;
    String instruments;
    String date_enrolled;
    List<GetStudentContactDto> contacts;
    List<StudentCourseDto> enrolled;

    public static GetStudentDto fromDomain(Student student){

        return new GetStudentDto(student.getId(), student.getStudentName().getFirstName(),
                student.getStudentName().getLastName(), student.getBirthday().getBirthday(),
                student.getInstruments().getInstruments(), student.getDateEnrolled(),
                student.getContactInfo().stream().map(GetStudentContactDto::fromDomain).collect(Collectors.toList()),
                student.getEnrolledIn().stream().map(StudentCourseDto::fromDomain).collect(Collectors.toList()));
    }
}
