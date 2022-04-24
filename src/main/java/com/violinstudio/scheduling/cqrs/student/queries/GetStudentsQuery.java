package com.violinstudio.scheduling.cqrs.student.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetStudentsQuery implements Command<List<Student>> {
}
