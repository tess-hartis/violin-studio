package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.course.CourseDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCourseDetailsDto {

    String id;
    String course_id;
    Boolean weekly;
    String day;
    String start_time;
    String end_time;
    String room_id;
    Float price;

    public static GetCourseDetailsDto fromDomain(CourseDetails cd){
        return new GetCourseDetailsDto(cd.getId(), cd.getCourseId(), cd.getWeekly(),
                cd.getDayOfWeek().getDay(), cd.getStartTime().getTime(), cd.getEndTime().getTime(),
                cd.getRoomId().getRoomId(), cd.getPrice().getAmount());
    }
}
