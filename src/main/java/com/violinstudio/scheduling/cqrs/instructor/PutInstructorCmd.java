package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.instructor.Bio;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.domain.student.Student;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PutInstructorCmd implements Command<Option<Validation<List<String>, Option<Instructor>>>> {

    String first_name;
    String last_name;
    String bio;
    String instructorId;

    public Validation<Seq<String>, Instructor> toDomain(Instructor instructor){

        var name = Name.validate(first_name, last_name);
        var b = Bio.validate(bio);

        return Validation.combine(name, b).ap(instructor::update);
    }

    public void setInstructorId(String id){
        this.instructorId = id;
    }
}

