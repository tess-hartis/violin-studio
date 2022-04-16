package com.violinstudio.scheduling.domain;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.stream.Collectors;

@Value
@RequiredArgsConstructor
public class StudentContact {

    @NonNull String id;
    @NonNull Boolean primaryContact;
    @NonNull Name name;
    @NonNull Email email;
    @NonNull Phone phone;
    @NonNull String studentId;


    private Boolean duplicatePrimaryContact(Student student){
        var duplicate = student
                .getContactInfo().stream()
                .filter(x -> x.primaryContact)
                .collect(Collectors.toList());

        return !duplicate.isEmpty();
    }

}
