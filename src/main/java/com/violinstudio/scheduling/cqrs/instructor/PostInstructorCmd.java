package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.instructor.Bio;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class PostInstructorCmd implements Command<Validation<List<String>, Instructor>> {

    String first_name;
    String last_name;
    String bio;

    public Validation<Seq<String>, Instructor> toDomain(){

        var name = Name.validate(first_name, last_name);
        var b = Bio.validate(bio);
        Validation<String, String> id = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> dateAdded = Validation.valid(LocalDate.now().toString());

        return Validation.combine(id, name, b, dateAdded).ap(Instructor::new);
    }

}
