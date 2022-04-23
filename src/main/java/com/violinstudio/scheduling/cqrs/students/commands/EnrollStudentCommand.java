package com.violinstudio.scheduling.cqrs.students.commands;


import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.CoursesRepository;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.control.Option;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnrollStudentCommand {

    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;

    public Option<Student> handle(String studentId, String courseId){

        var student = studentsRepository.findOne(studentId);
        var course = coursesRepository.findOne(courseId);

        return course.flatMap(c -> student.map(s -> studentsRepository.addCourse(s, c)));
    }
}
