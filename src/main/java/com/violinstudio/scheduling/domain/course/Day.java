package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Day {

    @NonNull String day;

    public static Validation<String, Day> validate(String day){

        if (day.isEmpty())
            return invalid("Invalid day");

        return valid(new Day(day));
    }

    public static Day unsafe(String day){
        return new Day(day);
    }
}
