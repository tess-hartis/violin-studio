package com.violinstudio.scheduling.domain.student;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public
class Birthday {

    @NonNull String birthday;

    public static Validation<String, Birthday> validate(String birthday) {


        if (birthday.isEmpty())
            return invalid("Invalid birthday");

        return valid(new Birthday(birthday));
    }

    public static Birthday unsafe(String birthday){
        return new Birthday(birthday);
    }
}
