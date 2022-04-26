package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AssignToCourseCmdHandler implements Command.Handler<AssignToCourseCmd, Option<Instructor>> {

    private final InstructorsRepository instructorsRepository;
    private final CoursesRepository coursesRepository;

    @Override
    public Option<Instructor> handle(AssignToCourseCmd command) {

        var instructor = instructorsRepository.findOne(command.instructorId);
        var course = coursesRepository.findOne(command.courseId);

        return course.flatMap(c -> instructor.map(i -> instructorsRepository.addCourse(i, c)));
    }
}
