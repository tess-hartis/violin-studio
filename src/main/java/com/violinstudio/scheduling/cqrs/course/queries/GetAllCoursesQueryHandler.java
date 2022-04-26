package com.violinstudio.scheduling.cqrs.course.queries;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor
public class GetAllCoursesQueryHandler implements Command.Handler<GetAllCoursesQuery, List<Course>> {

    private final CoursesRepository coursesRepository;

    public List<Course> handle(GetAllCoursesQuery query){

        return coursesRepository.findAll();
    }
}
