package com.violinstudio.scheduling.cqrs.course.commands;

import com.violinstudio.scheduling.domain.course.*;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PostCourseDetailsCommand {

    private final CoursesRepository coursesRepository;

    public Option<Validation<List<String>, CourseDetails>> handle(PostCourseDetailsDto dto){


        var course = coursesRepository.findOne(dto.courseId);
        return course.map(dto::toDomain)
                .map(c -> c.map(coursesRepository::addDetails)
                        .mapError(Value::toJavaList));

    }
}
