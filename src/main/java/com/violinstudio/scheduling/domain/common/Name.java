package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Name {

    @NonNull String firstName;
    @NonNull String lastName;

    public static Validation<String, Name> validate(String firstName, String lastName){

        return Validation.valid(new Name(firstName, lastName));
    }

    public static Name unsafe(String firstName, String lastName){
        return new Name(firstName, lastName);
    }
}
