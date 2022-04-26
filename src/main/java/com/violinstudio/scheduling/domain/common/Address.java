package com.violinstudio.scheduling.domain.common;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Address {

    @NonNull String streetAddress;
    @NonNull String city;
    @NonNull String state;
    @NonNull String zipcode;

    public static Validation<String, Address> validate(String streetAddress, String city, String state, String zipcode){
        return Validation.valid(new Address(streetAddress, city, state, zipcode));
    }

    public static Address unsafe(String streetAddress, String city, String state, String zipcode){
        return new Address(streetAddress, city, state, zipcode);
    }
}
