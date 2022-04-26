package com.violinstudio.scheduling.cqrs.course.queries;


import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetOneCourseQueryHandler implements Command.Handler<GetOneCourseQuery, Option<Course>>{

    private final CoursesRepository coursesRepository;

    public Option<Course> handle(GetOneCourseQuery query){

        return coursesRepository.findOne(query.id);
    }
}
