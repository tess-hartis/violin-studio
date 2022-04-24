package com.violinstudio.scheduling.repository.instructor;

import com.violinstudio.scheduling.domain.common.Address;
import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class InstructorContactMapper implements RowMapper<InstructorContact> {

    @Override
    public InstructorContact mapRow(ResultSet rs, int rowNum) throws SQLException {

        var id = rs.getString("id");
        var address = Address.unsafe(
                rs.getString("street_address"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("zipcode"));

        var email = Email.unsafe(rs.getString("email"));
        var phone = Phone.unsafe(rs.getString("phone"));
        var instructorId = rs.getString("instructor_id");

        return new InstructorContact(id, email, phone, address, instructorId);
    }
}
