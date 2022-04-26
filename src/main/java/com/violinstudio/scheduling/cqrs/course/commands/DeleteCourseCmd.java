package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteCourseCmd implements Command<Integer> {

    String id;
}
