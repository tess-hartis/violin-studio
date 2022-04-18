package com.violinstudio.scheduling.rest;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.student.ContactType;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.domain.student.StudentContact;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Data
public class PostPrimaryContactDto {

    String first_name;
    String last_name;
    String email;
    String phone;

    public Validation<Seq<String>, StudentContact> toDomain(Student s){

        var n = Name.validate(first_name, last_name);
        var e = Email.validate(email);
        var p = Phone.validate(phone);
        Validation<String, String> nanoid = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> studentId = Validation.valid(s.getId());
        var ct = checkForPrimaryContact(s);

        return Validation.combine(nanoid, ct, n, e, p, studentId)
                .ap(StudentContact::new);
    }

    Validation<String, ContactType> checkForPrimaryContact(Student s) {

        var hasPrimaryContact = s.getContactInfo()
                .stream().filter(x -> x.getPrimaryContact().getType().equals("Primary")).collect(Collectors.toList());

        if (!hasPrimaryContact.isEmpty()) {

            return Validation.invalid("Student already has a primary contact");
        }

        return Validation.valid(ContactType.unsafe("Primary"));
    }
}
