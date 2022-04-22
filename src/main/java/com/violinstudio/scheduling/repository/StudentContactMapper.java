package com.violinstudio.scheduling.repository;

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
        var pc = ContactType.unsafe(rs.getString("contact_type"));
        var n = Name.unsafe(rs.getString("first_name"), rs.getString("last_name"));
        var e = Email.unsafe(rs.getString("email"));
        var p = Phone.unsafe(rs.getString("phone"));
        var si = rs.getString("student_id");

        return new StudentContact(id, pc, n, e, p, si);
    }
}
