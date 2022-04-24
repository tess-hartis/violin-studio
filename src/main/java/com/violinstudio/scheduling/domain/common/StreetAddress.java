package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class StreetAddress {

    @NonNull String value;

    public static Validation<String, StreetAddress> validate(String value){
        return Validation.valid(new StreetAddress(value));
    }

    public static StreetAddress unsafe(String value){
        return new StreetAddress(value);
    }
}
