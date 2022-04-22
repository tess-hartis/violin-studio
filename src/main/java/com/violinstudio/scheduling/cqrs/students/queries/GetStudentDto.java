package com.violinstudio.scheduling.cqrs.students.queries;

import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.cqrs.students.commands.StudentCourseDto;
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

//    public Student toDomain(){
//
//        var n = Name.unsafe(first_name, last_name);
//        var b = Birthday.unsafe(birthday);
//        var ins = Instruments.unsafe(instruments);
//        var nanoid = id;
//        var de = date_enrolled;
//
//        return new Student(nanoid, n, b, ins, de);
//    }

    public static GetStudentDto fromDomain(Student student){

        return new GetStudentDto(student.getId(), student.getStudentName().getFirstName(),
                student.getStudentName().getLastName(), student.getBirthday().getBirthday(),
                student.getInstruments().getInstruments(), student.getDateEnrolled(),
                student.getContactInfo().stream().map(GetStudentContactDto::fromDomain).collect(Collectors.toList()),
                student.getEnrolledIn().stream().map(StudentCourseDto::fromDomain).collect(Collectors.toList()));
    }
}

//data annotations adds getters and setters. Jackson cannot serialize without getters or setters.
//Was getting error "com.fasterxml.jackson.databind.exc.InvalidDefinitionException:
// No serializer found for class org.json.JSONObject and no properties discovered to create BeanSerializer"
