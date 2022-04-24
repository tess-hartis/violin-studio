package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class City {

    @NonNull String value;

    public static Validation<String, City> validate(String value){
        return Validation.valid(new City(value));
    }

    public static City unsafe(String value){
        return new City(value);
    }
}
