package com.violinstudio.scheduling.cqrs.course.commands;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.Value;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class PutCourseCommand {

    private final CoursesRepository coursesRepository;

    public Option<Validation<List<String>, Course>> handle(PutCourseDto dto){

        var course = coursesRepository.findOne(dto.courseId);
        return course.map(dto::toDomain)
                .map(c -> c.map(coursesRepository::update)
                        .mapError(Value::toJavaList));
    }

}
