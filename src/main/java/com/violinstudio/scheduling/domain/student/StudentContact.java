package com.violinstudio.scheduling.domain.student;

import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import lombok.*;

import java.util.stream.Collectors;

@Value
public class StudentContact {

    @NonNull String id;
    @NonNull @With ContactType contactType;
    @NonNull @With Name name;
    @NonNull @With Email email;
    @NonNull @With Phone phone;
    @NonNull String studentId;

}
