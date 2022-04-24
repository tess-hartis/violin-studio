package com.violinstudio.scheduling.domain.instructor;

import com.violinstudio.scheduling.domain.common.Address;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Phone;
import lombok.NonNull;
import lombok.Value;

@Value
public class InstructorContact {

    @NonNull String id;
    @NonNull Email email;
    @NonNull Phone phone;
    @NonNull Address address;
}
