package com.violinstudio.scheduling.cqrs.course.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllCoursesQuery implements Command<List<Course>> {
}
