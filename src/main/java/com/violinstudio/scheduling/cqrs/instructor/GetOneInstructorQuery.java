package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetOneInstructorQuery implements Command<Option<Instructor>> {

    String id;
}
