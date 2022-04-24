package com.violinstudio.scheduling.cqrs.course.queries;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.repository.course.CoursesRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class GetAllCoursesQuery {

    private final CoursesRepository coursesRepository;

    public List<Course> handle(){

        return coursesRepository.findAll();
    }
}
