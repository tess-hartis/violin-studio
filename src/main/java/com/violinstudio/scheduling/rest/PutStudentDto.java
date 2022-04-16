package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.Birthday;
import com.violinstudio.scheduling.domain.Instruments;
import com.violinstudio.scheduling.domain.Name;
import com.violinstudio.scheduling.domain.Student;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PutStudentDto {

    String first_name;
    String last_name;
    String birthday;
    String instruments;

    public Validation<Seq<String>, Student> toDomain(Student student){

        var name = Name.validate(first_name, last_name);
        var bd = Birthday.validate(birthday);
        var i = Instruments.validate(instruments);

        return Validation.combine(name, bd, i).ap(student::update);
    }
}
