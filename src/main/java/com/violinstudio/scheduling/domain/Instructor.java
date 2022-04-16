package com.violinstudio.scheduling.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Value
public class Instructor {

    String id;
    @NonNull Name firstName;
    @NonNull Name lastName;
    @NonNull Email email;
    @NonNull Phone phone;
    @NonNull Bio bio;
    String dateAdded;
    List<Course> classes = new ArrayList<>();

    private Instructor(Name firstName, Name lastName, Email email, Phone phone, Bio bio){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
        dateAdded = LocalDate.now().toString();
        id = NanoIdUtils.randomNanoId();
    }

    public static Instructor of(Name firstName, Name lastName, Email email, Phone phone, Bio bio){
        return new Instructor(firstName, lastName, email, phone, bio);
    }
}

@Value
class Bio{
    @NonNull String bio;

    public static Validation<String, Bio> of(String bio){
        return Validation.valid(new Bio(bio));
    }
}
