package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Day {

    @NonNull String day;

    public static Validation<String, Day> validate(String day){
        return Validation.valid(new Day(day));
    }

    public static Day unsafe(String day){
        return new Day(day);
    }
}
