package com.violinstudio.scheduling.domain.instructor;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Bio {

    @NonNull String value;

    public static Validation<String, Bio> validate(String value) {

        if (value.isEmpty())
            return invalid("Invalid bio");

        return valid(new Bio(value));
    }

    public static Bio unsafe(String value){
        return new Bio(value);
    }
}
