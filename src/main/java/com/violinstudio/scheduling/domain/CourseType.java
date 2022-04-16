package com.violinstudio.scheduling.domain;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
public class CourseType {

    @NonNull String value;

//    private enum CourseTypeEnum{
//    PrivateLesson, GroupClass}

    public static Validation<String, CourseType> validate(String value){
        return Validation.valid(new CourseType(value));
    }

    public static CourseType unsafe(String value){
        return new CourseType(value);
    }
}
