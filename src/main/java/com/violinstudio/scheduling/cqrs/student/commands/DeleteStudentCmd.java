package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteStudentCmd implements Command<Integer> {

    public String id;
}
