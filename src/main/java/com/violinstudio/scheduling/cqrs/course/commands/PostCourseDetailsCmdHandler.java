package com.violinstudio.scheduling.cqrs.course.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.*;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class PostCourseDetailsCmdHandler implements Command.Handler<PostCourseDetailsCmd, Option<Validation<List<String>, CourseDetails>>> {

    private final CoursesRepository coursesRepository;

    public Option<Validation<List<String>, CourseDetails>> handle(PostCourseDetailsCmd dto){


        var course = coursesRepository.findOne(dto.courseId);
        return course.map(dto::toDomain)
                .map(c -> c.map(coursesRepository::addDetails)
                        .mapError(Value::toJavaList));

    }
}
