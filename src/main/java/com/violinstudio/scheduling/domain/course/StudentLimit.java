package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;


@Value
public class StudentLimit {

    @NonNull Integer value;

    public static Validation<String, StudentLimit> validate(Integer value){

        if (value > 150)
            return invalid("Invalid student limit");

        return valid(new StudentLimit(value));
    }

    public static StudentLimit unsafe(Integer value){
        return new StudentLimit(value);
    }
}
