package com.violinstudio.scheduling.cqrs.students.commands;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class PostPrimaryContactDto {

    String student_id;
    String first_name;
    String last_name;
    String email;
    String phone;

}


