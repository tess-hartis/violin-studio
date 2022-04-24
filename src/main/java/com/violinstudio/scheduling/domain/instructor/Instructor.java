package com.violinstudio.scheduling.domain.instructor;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.course.Course;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class Instructor {

    @NonNull String id;
    @NonNull @With Name name;
    @NonNull @With Bio bio;
    String dateAdded;
    List<Course> classes = new ArrayList<>();
    List<InstructorContact> contacts = new ArrayList<>();

    public Instructor update(Name name, Bio bio){
        return this.withName(name).withBio(bio);
    }

}

