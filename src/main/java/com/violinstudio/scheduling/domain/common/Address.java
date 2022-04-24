package com.violinstudio.scheduling.domain.common;

import lombok.NonNull;
import lombok.Value;

@Value
public class Address {

    @NonNull StreetAddress streetAddress;
    @NonNull City city;
    @NonNull State state;
    @NonNull Zipcode zipcode;
}
