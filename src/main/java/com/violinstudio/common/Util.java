package com.violinstudio.common;

import io.vavr.control.Validation;

public class Util {

    public static Validation<String, Boolean> toValidation(Boolean b){
        return Validation.valid(b);
    }

    public static Integer toInt(Boolean b){
        if (b)
            return 1;

        else return 0;
    }

}
