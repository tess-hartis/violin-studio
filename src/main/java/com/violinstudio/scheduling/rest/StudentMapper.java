package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.*;
import com.violinstudio.scheduling.rest.GetStudentDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var n = Name.unsafe(rs.getString("first_name"),rs.getString("last_name"));
        var bd = Birthday.unsafe(rs.getString("birthday"));
        var i = Instruments.unsafe(rs.getString("instruments"));
        var de = rs.getString("date_enrolled");

        return new Student(id,n, bd, i, de);
    }
}
