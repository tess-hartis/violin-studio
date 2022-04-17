package com.violinstudio.scheduling.domain;

import com.violinstudio.common.Money;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;

import java.sql.Time;
import java.time.DayOfWeek;

@Value
@RequiredArgsConstructor
public class CourseDetails {

    @NonNull String id;
    @NonNull String courseId;
    @NonNull @With Boolean weekly;
    @NonNull @With Day dayOfWeek;
    @NonNull @With CourseTime startTime;
    @NonNull @With CourseTime endTime;
    @NonNull @With RoomId roomId;
    @NonNull @With Money price;

    public CourseDetails udpate(Boolean weekly, Day dayOfWeek, CourseTime startTime, CourseTime endTime,
                                RoomId roomId, Money price){
        return this.withWeekly(weekly).withDayOfWeek(dayOfWeek).withStartTime(startTime).withEndTime(endTime)
                .withRoomId(roomId).withPrice(price);
    }
}