package com.violinstudio.scheduling.rest;

import com.violinstudio.common.Money;
import com.violinstudio.scheduling.domain.Course;
import com.violinstudio.scheduling.domain.CourseType;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class CourseMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        var id = rs.getString("id");
        var t = CourseType.unsafe(rs.getString("course_type"));
        var d = rs.getString("description");

        return new Course(id, t, d);
    }
}
