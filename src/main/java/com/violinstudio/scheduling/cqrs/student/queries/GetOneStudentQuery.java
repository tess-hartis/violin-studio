package com.violinstudio.scheduling.cqrs.student.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetOneStudentQuery implements Command<Option<Student>> {

    public String id;
}
