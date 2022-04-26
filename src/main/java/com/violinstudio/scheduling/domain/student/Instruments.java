package com.violinstudio.scheduling.domain.student;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public
class Instruments {

    @NonNull String instruments;

    public static Validation<String, Instruments> validate(String instruments) {
        return Validation.valid(new Instruments(instruments));
    }

    public static Instruments unsafe(String instruments){
        return new Instruments(instruments);
    }
}
