package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteInstructorCmd implements Command<Integer> {

    public String id;
}
