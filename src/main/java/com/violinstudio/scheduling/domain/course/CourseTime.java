package com.violinstudio.scheduling.domain.course;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class CourseTime {

    @NonNull String time;

    public static Validation<String, CourseTime> validate(String time){
        return Validation.valid(new CourseTime(time));
    }

    public static CourseTime unsafe(String time){
        return new CourseTime(time);
    }
}
