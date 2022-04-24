package com.violinstudio.scheduling.cqrs.course.commands;

import com.violinstudio.scheduling.repository.course.CoursesRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DeleteCourseCommand {

    private final CoursesRepository coursesRepository;

    public Integer handle(String courseId){
        return coursesRepository.deleteOne(courseId);
    }
}
