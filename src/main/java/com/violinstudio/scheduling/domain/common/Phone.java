package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Phone {

    @NonNull String value;

    public static Validation<String, Phone> validate(String value){
        return Validation.valid(new Phone(value));
    }

    public static Phone unsafe(String value){ return new Phone(value);}
}
