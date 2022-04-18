package com.violinstudio.scheduling.domain.student;

import com.violinstudio.common.Util;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class ContactType {

    private enum ContactTypeEnum{
        Primary, Secondary
    }

    @NonNull String type;

    public static Validation<String, ContactType> validate(String type){
        if (!Util.isInEnum(type, ContactTypeEnum.class)){
            return Validation.invalid("Contact type must be 'Primary' or 'Secondary'");
        }

        return Validation.valid(new ContactType(type));
    }

    public static ContactType unsafe(String type){
        return new ContactType(type);
    }
}
