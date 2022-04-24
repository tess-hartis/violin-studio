package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnrollStudentCmd implements Command<Option<Student>> {

    String studentId;
    String courseId;
}
