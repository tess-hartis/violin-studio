package com.violinstudio.scheduling.domain.instructor;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Bio {

    @NonNull String value;

    public static Validation<String, Bio> validate(String value) {
        return Validation.valid(new Bio(value));
    }

    public static Bio unsafe(String value){
        return new Bio(value);
    }
}
