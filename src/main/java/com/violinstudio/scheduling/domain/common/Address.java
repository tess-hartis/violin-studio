package com.violinstudio.scheduling.domain.common;

import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;
import static io.vavr.control.Validation.valid;
import static io.vavr.control.Validation.invalid;

@Value
public class Address {

    @NonNull String streetAddress;
    @NonNull String city;
    @NonNull String state;
    @NonNull String zipcode;

    public static Validation<String, Address> validate(String streetAddress, String city, String state, String zipcode){

        if (streetAddress.isEmpty())
            return invalid("Invalid address");

        if (city.isEmpty())
            return invalid("Invalid address");

        if (state.isEmpty())
            return invalid("Invalid address");

        if (zipcode.isEmpty())
            return invalid("Invalid address");

        return valid(new Address(streetAddress, city, state, zipcode));
    }

    public static Address unsafe(String streetAddress, String city, String state, String zipcode){
        return new Address(streetAddress, city, state, zipcode);
    }
}
