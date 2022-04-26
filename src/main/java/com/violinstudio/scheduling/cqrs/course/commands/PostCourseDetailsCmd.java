package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.violinstudio.common.Money;
import com.violinstudio.scheduling.domain.course.*;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
public class PostCourseDetailsCmd implements Command<Option<Validation<List<String>, CourseDetails>>> {

    String courseId;
    Boolean weekly;
    String day_of_week;
    String start_time;
    String end_time;
    String room_id;
    Float price;

    public Validation<Seq<String>, CourseDetails> toDomain(Course c){

        Validation<String, Boolean> w = Validation.valid(weekly);
        var d = Day.validate(day_of_week);
        var st = CourseTime.validate(start_time);
        var et = CourseTime.validate(end_time);
        var r = RoomId.validate(room_id);
        var p = Money.validate(price);
        Validation<String, String> id = Validation.valid(NanoIdUtils.randomNanoId());
        Validation<String, String> courseId = Validation.valid(c.getId());

        return Validation.combine(id, courseId, w, d, st, et, r, p)
                .ap(CourseDetails::new);
    }

    public void setCourseId(String id){
        this.courseId = id;
    }

}
