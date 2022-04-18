package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;


@Value
public class StudentLimit {

    @NonNull Integer value;

    public static Validation<String, StudentLimit> validate(Integer value){
        return Validation.valid(new StudentLimit(value));
    }

    public static StudentLimit unsafe(Integer value){
        return new StudentLimit(value);
    }
}
