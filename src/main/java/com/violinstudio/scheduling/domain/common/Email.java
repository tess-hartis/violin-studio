package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Email {

    @NonNull String value;

    public static Validation<String, Email> validate(String value){
        return Validation.valid(new Email(value));
    }

    public static Email unsafe(String value){
        return new Email(value);
    }
}

