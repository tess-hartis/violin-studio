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

@Data
@Component
public class PostSecondaryContactDto {

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
        Validation<String, ContactType> ct = Validation.valid(ContactType.unsafe("Secondary"));

        return Validation.combine(nanoid, ct, n, e, p, studentId)
                .ap(StudentContact::new);
    }
}
