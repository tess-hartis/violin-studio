package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Zipcode {

    @NonNull String value;

    public static Validation<String, Zipcode> validate(String value){
        return Validation.valid(new Zipcode(value));
    }


    public static Zipcode unsafe(String value){
        return new Zipcode(value);
    }
}
