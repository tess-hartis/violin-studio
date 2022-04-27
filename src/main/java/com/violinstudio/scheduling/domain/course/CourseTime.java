package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class CourseTime {

    @NonNull String time;

    public static Validation<String, CourseTime> validate(String time){

        if (time.isEmpty())
            return invalid("Invalid time");

        return valid(new CourseTime(time));
    }

    public static CourseTime unsafe(String time){
        return new CourseTime(time);
    }
}
