package com.violinstudio.scheduling.rest;

import com.violinstudio.common.Money;
import com.violinstudio.scheduling.domain.CourseDetails;
import com.violinstudio.scheduling.domain.CourseTime;
import com.violinstudio.scheduling.domain.Day;
import com.violinstudio.scheduling.domain.RoomId;
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
        var cid = rs.getString("course_id");
        var w = rs.getBoolean("weekly");
        var d = Day.unsafe(rs.getString("day_of_week"));
        var st = CourseTime.unsafe(rs.getString("start_time"));
        var et = CourseTime.unsafe(rs.getString("end_time"));
        var r = RoomId.unsafe(rs.getString("room_id"));
        var p = Money.unsafe(rs.getFloat("price"));

        return new CourseDetails(id, cid, w, d, st, et, r, p);
    }

}
