package com.violinstudio.scheduling.cqrs.course.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetOneCourseQuery implements Command<Option<Course>> {

    String id;
}
