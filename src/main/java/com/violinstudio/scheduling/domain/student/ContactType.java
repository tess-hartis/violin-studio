package com.violinstudio.scheduling.domain.student;

import com.violinstudio.common.Util;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class ContactType {

    private enum ContactTypeEnum{
        Primary, Secondary
    }

    @NonNull String type;

    public static Validation<String, ContactType> validate(String type){

        if (!Util.isInEnum(type, ContactTypeEnum.class)){
            return invalid("Contact type must be 'Primary' or 'Secondary'");
        }

        return valid(new ContactType(type));
    }

    public static ContactType unsafe(String type){
        return new ContactType(type);
    }
}
