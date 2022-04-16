package com.violinstudio.scheduling.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.*;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PostStudentContactDto {

    Boolean primary_contact;
    String first_name;
    String last_name;
    String email;
    String phone;

    public Validation<Seq<String>, StudentContact> toDomain(Student s){

        var n = Name.validate(first_name, last_name);
        var e = Email.validate(email);
        var p = Phone.validate(phone);
        Validation<String, Boolean> pc = Validation.valid(primary_contact);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> studentId = Validation.valid(s.getId());

        return Validation.combine(nanoid, pc, n, e, p, studentId)
                .ap(StudentContact::new);
    }
}
