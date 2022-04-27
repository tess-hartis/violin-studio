package com.violinstudio.scheduling.repository.student;

import com.violinstudio.scheduling.domain.common.Email;
import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.common.Phone;
import com.violinstudio.scheduling.domain.student.ContactType;
import com.violinstudio.scheduling.domain.student.StudentContact;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentContactMapper implements RowMapper<StudentContact> {

    @Override
    public StudentContact mapRow(ResultSet rs, int rowNum) throws SQLException {

        var id = rs.getString("id");
        var contactType = ContactType.unsafe(rs.getString("contact_type"));
        var name = Name.unsafe(rs.getString("first_name"), rs.getString("last_name"));
        var email = Email.unsafe(rs.getString("email"));
        var phone = Phone.unsafe(rs.getString("phone"));
        var studentId = rs.getString("student_id");

        return new StudentContact(id, contactType, name, email, phone, studentId);
    }
}
