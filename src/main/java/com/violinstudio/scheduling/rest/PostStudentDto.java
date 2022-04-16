package com.violinstudio.scheduling.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.Birthday;
import com.violinstudio.scheduling.domain.Instruments;
import com.violinstudio.scheduling.domain.Name;
import com.violinstudio.scheduling.domain.Student;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class PostStudentDto {
    String first_name;
    String last_name;
    String birthday;
    String instruments;

    public Validation<Seq<String>, Student> toDomain(){

        var n = Name.validate(first_name, last_name);
        var b = Birthday.validate(birthday);
        var ins = Instruments.validate(instruments);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> de = Validation.valid(LocalDate.now().toString());

        return Validation.combine(nanoid, n, b, ins, de).ap(Student::new);
    }
}
