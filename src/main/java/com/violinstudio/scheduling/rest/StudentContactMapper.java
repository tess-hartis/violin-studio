package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.*;
import lombok.NonNull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentContactMapper implements RowMapper<StudentContact> {

    @Override
    public StudentContact mapRow(ResultSet rs, int rowNum) throws SQLException {

        var id = rs.getString("id");
        var pc = rs.getBoolean("primary_contact");
        var n = Name.unsafe(rs.getString("first_name"), rs.getString("last_name"));
        var e = Email.unsafe(rs.getString("email"));
        var p = Phone.unsafe(rs.getString("phone"));
        var si = rs.getString("student_id");

        return new StudentContact(id, pc, n, e, p, si);
    }
}
