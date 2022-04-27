package com.violinstudio.scheduling.repository.course;

import com.violinstudio.common.Money;
import com.violinstudio.scheduling.domain.course.CourseDetails;
import com.violinstudio.scheduling.domain.course.CourseTime;
import com.violinstudio.scheduling.domain.course.Day;
import com.violinstudio.scheduling.domain.course.RoomId;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
@Component
public class CourseDetailsMapper implements RowMapper<CourseDetails> {

    @Override
    public CourseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

        var id = rs.getString("id");
        var courseId = rs.getString("course_id");
        var weekly = rs.getBoolean("weekly");
        var dayOfWeek = Day.unsafe(rs.getString("day_of_week"));
        var startTime = CourseTime.unsafe(rs.getString("start_time"));
        var endTime = CourseTime.unsafe(rs.getString("end_time"));
        var roomId = RoomId.unsafe(rs.getString("room_id"));
        var price = Money.unsafe(rs.getFloat("price"));

        return new CourseDetails(id, courseId, weekly, dayOfWeek, startTime, endTime, roomId, price);
    }

}
