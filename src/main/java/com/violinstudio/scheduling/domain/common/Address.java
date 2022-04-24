package com.violinstudio.scheduling.domain.common;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.NonNull;
import lombok.Value;

@Value
public class Address {

    @NonNull StreetAddress streetAddress;
    @NonNull City city;
    @NonNull State state;
    @NonNull Zipcode zipcode;

    public static Validation<Seq<String>, Address> validate(String streetAddress, String city, String state, String zipcode){

        var sa = StreetAddress.validate(streetAddress);
        var c = City.validate(city);
        var s = State.validate(state);
        var z = Zipcode.validate(zipcode);

        return Validation.combine(sa, c, s, z).ap(Address::new);
    }

    public static Address unsafe(String streetAddress, String city, String state, String zipcode){
        return new Address(StreetAddress.unsafe(streetAddress), City.unsafe(city), State.unsafe(state), Zipcode.unsafe(zipcode));
    }
}
