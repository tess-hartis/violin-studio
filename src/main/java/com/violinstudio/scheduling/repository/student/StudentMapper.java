package com.violinstudio.scheduling.repository.student;

import com.violinstudio.scheduling.domain.common.Name;
import com.violinstudio.scheduling.domain.student.Birthday;
import com.violinstudio.scheduling.domain.student.Instruments;
import com.violinstudio.scheduling.domain.student.Student;
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
        var name = Name.unsafe(rs.getString("first_name"),rs.getString("last_name"));
        var birthday = Birthday.unsafe(rs.getString("birthday"));
        var instruments = Instruments.unsafe(rs.getString("instruments"));
        var dateEnrolled = rs.getString("date_enrolled");

        return new Student(id,name, birthday, instruments, dateEnrolled);
    }
}
