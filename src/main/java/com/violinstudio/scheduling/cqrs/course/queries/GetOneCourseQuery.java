package com.violinstudio.scheduling.cqrs.course.queries;


import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.control.Option;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GetOneCourseQuery {

    private final CoursesRepository coursesRepository;

    public Option<Course> handle(String id){

        return coursesRepository.findOne(id);
    }
}
