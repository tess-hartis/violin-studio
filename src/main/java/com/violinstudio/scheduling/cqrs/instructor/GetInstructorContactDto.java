package com.violinstudio.scheduling.cqrs.instructor;

import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetInstructorContactDto {

    String address;
    String city;
    String state;
    String zipcode;
    String email;
    String phone;

    public static GetInstructorContactDto fromDomain(InstructorContact i){

        return new GetInstructorContactDto(

                i.getAddress().getStreetAddress(),
                i.getAddress().getCity(),
                i.getAddress().getState(),
                i.getAddress().getZipcode(),
                i.getEmail().getValue(),
                i.getPhone().getValue()
        );
    }
}
