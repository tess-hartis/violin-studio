package com.violinstudio.scheduling.cqrs.student.commands;


import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class EnrollStudentCmdHandler implements Command.Handler<EnrollStudentCmd, Option<Student>> {

    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;

    public Option<Student> handle(EnrollStudentCmd command){

        var student = studentsRepository.findOne(command.studentId);
        var course = coursesRepository.findOne(command.courseId);

        return course.flatMap(c -> student.map(s -> studentsRepository.addCourse(s, c)));
    }
}
