package com.violinstudio.scheduling.domain;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;


@Value
public class StudentLimit {

    @NonNull Integer value;

    public static Validation<String, Integer> validate(Integer value){
        return Validation.valid(value);
    }

    public static StudentLimit unsafe(Integer value){
        return new StudentLimit(value);
    }
}
