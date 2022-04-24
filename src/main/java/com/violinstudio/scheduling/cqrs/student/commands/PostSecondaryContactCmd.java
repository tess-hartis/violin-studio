package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.StudentContact;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PostSecondaryContactCmd implements Command<Option<Validation<List<String>, StudentContact>>> {

    String student_id;
    String first_name;
    String last_name;
    String email;
    String phone;

    public void setStudentId(String id){
        this.student_id = id;
    }
}
