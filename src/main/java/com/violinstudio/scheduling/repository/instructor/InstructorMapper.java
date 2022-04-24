package com.violinstudio.scheduling.repository.instructor;

import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.instructor.Bio;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class InstructorMapper implements RowMapper<Instructor> {

    @Override
    public Instructor mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var name = Name.unsafe(rs.getString("first_name"), rs.getString("last_name"));
        var bio = Bio.unsafe(rs.getString("bio"));
        var dateAdded = rs.getString("date_added");

        return new Instructor(id, name, bio, dateAdded);
    }
}
