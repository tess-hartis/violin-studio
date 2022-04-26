package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetInstructorsQuery implements Command<List<Instructor>> {
}
