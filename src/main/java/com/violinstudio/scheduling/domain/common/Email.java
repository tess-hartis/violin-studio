package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Email {

    @NonNull String value;

    public static Validation<String, Email> validate(String value){

        if (value.isEmpty())
            return invalid("Invalid email");

        return valid(new Email(value));
    }

    public static Email unsafe(String value){
        return new Email(value);
    }
}

