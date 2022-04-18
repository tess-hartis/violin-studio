package com.violinstudio.scheduling.domain.student;

import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.stream.Collectors;

@Value
@RequiredArgsConstructor
public class StudentContact {

    @NonNull String id;
    @NonNull ContactType primaryContact;
    @NonNull Name name;
    @NonNull Email email;
    @NonNull Phone phone;
    @NonNull String studentId;

}
