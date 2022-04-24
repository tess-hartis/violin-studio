package com.violinstudio.scheduling.cqrs.course.commands;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.Value;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PostCourseCommand {

    private final CoursesRepository coursesRepository;

    public Validation<List<String>, Course> handle(PostCourseDto dto){

        return dto.toDomain()
                .map(coursesRepository::saveNew)
                .mapError(Value::toJavaList);
    }
}
