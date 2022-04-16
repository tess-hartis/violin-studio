package com.violinstudio.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Money {

    @NonNull Float amount;

    public static Validation<String, Money> of(Float amount){
        return Validation.valid(new Money(amount));
    }

    public static Money unsafe(Float amount){
        return new Money(amount);
    }
}
