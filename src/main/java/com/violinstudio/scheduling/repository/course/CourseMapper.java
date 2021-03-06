package com.violinstudio.scheduling.repository.course;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseType;
import com.violinstudio.scheduling.domain.course.StudentLimit;
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
        var courseType = CourseType.unsafe(rs.getString("course_type"));
        var description = rs.getString("description");
        var studentLimit = StudentLimit.unsafe(rs.getInt("student_limit"));

        return new Course(id, courseType, description, studentLimit);
    }
}
