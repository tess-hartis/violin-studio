package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class CourseType {

    @NonNull String value;

    public static Validation<String, CourseType> validate(String value){

        if (value.isEmpty())
            return invalid("Invalid course type");

        return valid(new CourseType(value));
    }

    public static CourseType unsafe(String value){
        return new CourseType(value);
    }
}
