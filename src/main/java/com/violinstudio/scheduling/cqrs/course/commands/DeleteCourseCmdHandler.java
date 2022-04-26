package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteCourseCmdHandler implements Command.Handler<DeleteCourseCmd, Integer> {

    private final CoursesRepository coursesRepository;

    @Override
    public Integer handle(DeleteCourseCmd command) {
        return coursesRepository.deleteOne(command.id);
    }
}
