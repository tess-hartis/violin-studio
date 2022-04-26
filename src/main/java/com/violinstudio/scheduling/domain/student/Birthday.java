package com.violinstudio.scheduling.domain.student;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public
class Birthday {

    @NonNull String birthday;

    public static Validation<String, Birthday> validate(String birthday) {

        return Validation.valid(new Birthday(birthday));
    }

    public static Birthday unsafe(String birthday){
        return new Birthday(birthday);
    }
}
