package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class State {

    @NonNull String value;

    public static Validation<String, State> validate(String value){
        return Validation.valid(new State(value));
    }

    public static State unsafe(String value){
        return new State(value);
    }
}
