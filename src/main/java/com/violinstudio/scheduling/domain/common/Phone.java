package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Phone {

    @NonNull String value;

    public static Validation<String, Phone> validate(String value){

        if (value.isEmpty())
            return invalid("Invalid phone number");

        return valid(new Phone(value));
    }

    public static Phone unsafe(String value){ return new Phone(value);}
}
