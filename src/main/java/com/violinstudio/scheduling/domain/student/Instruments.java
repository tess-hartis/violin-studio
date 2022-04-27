package com.violinstudio.scheduling.domain.student;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public
class Instruments {

    @NonNull String instruments;

    public static Validation<String, Instruments> validate(String instruments) {

        if (instruments.isEmpty())
            return invalid("Invalid instruments");

        return valid(new Instruments(instruments));
    }

    public static Instruments unsafe(String instruments){
        return new Instruments(instruments);
    }
}
