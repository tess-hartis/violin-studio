package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Name {

    @NonNull String firstName;
    @NonNull String lastName;

    public static Validation<String, Name> validate(String firstName, String lastName){

        if (firstName.isEmpty())
            return invalid("Invalid first name");

        if (lastName.isEmpty())
            return invalid("Invalid last name");

        return valid(new Name(firstName, lastName));
    }

    public static Name unsafe(String firstName, String lastName){
        return new Name(firstName, lastName);
    }
}
