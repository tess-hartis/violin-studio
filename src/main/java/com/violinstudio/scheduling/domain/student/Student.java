package com.violinstudio.scheduling.domain.student;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.common.Name;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
public class Student{

    @NonNull  String id;
    @NonNull @With
    Name studentName;
    @NonNull @With
    Birthday birthday;
    @NonNull @With
    Instruments instruments;
    @NonNull String dateEnrolled;

    List<StudentContact> contactInfo = new ArrayList<>();
    List<Course> enrolledIn = new ArrayList<>();

    public Student update(Name name, Birthday birthday, Instruments instruments){

        return this.withStudentName(name).withBirthday(birthday).withInstruments(instruments);
    }

}


