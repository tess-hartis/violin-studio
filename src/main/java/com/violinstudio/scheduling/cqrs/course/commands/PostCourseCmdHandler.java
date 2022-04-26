package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.Value;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class PostCourseCmdHandler implements Command.Handler<PostCourseCmd, Validation<List<String>, Course>> {

    private final CoursesRepository coursesRepository;

    public Validation<List<String>, Course> handle(PostCourseCmd dto){

        return dto.toDomain()
                .map(coursesRepository::saveNew)
                .mapError(Value::toJavaList);
    }
}
