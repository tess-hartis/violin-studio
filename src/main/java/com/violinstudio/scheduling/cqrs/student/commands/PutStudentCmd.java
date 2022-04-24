package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Birthday;
import com.violinstudio.scheduling.domain.student.Instruments;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.student.Student;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PutStudentCmd implements Command<Option<Validation<List<String>, Student>>> {

    String first_name;
    String last_name;
    String birthday;
    String instruments;
    String studentId;

    public Validation<Seq<String>, Student> toDomain(Student student){

        var name = Name.validate(first_name, last_name);
        var bd = Birthday.validate(birthday);
        var i = Instruments.validate(instruments);

        return Validation.combine(name, bd, i).ap(student::update);
    }

    public void setStudentId(String id){
        this.studentId = id;
    }
}
