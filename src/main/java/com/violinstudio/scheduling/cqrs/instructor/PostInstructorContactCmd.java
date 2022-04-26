package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PostInstructorContactCmd implements Command<Option<Validation<List<String>, InstructorContact>>> {

    String street_address;
    String city;
    String state;
    String zipcode;
    String email;
    String phone;
    String instructorId;

    public void setInstructorId(String id){
        this.instructorId = id;
    }
}
