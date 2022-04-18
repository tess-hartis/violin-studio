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

    public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if(e.name().equals(value)) { return true; }
        }
        return false;
    }

}
