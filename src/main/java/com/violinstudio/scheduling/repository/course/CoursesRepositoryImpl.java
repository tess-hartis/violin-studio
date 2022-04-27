package com.violinstudio.scheduling.repository.course;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.course.CourseDetails;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.repository.instructor.InstructorMapper;
import com.violinstudio.scheduling.repository.student.StudentMapper;
import io.vavr.control.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public class CoursesRepositoryImpl implements CoursesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CourseMapper courseMapper;
    private final CourseDetailsMapper courseDetailsMapper;
    private final StudentMapper studentMapper;
    private final InstructorMapper instructorMapper;

    public CoursesRepositoryImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper,
                                 CourseDetailsMapper courseDetailsMapper, StudentMapper studentMapper, InstructorMapper instructorMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.courseDetailsMapper = courseDetailsMapper;
        this.studentMapper = studentMapper;
        this.instructorMapper = instructorMapper;
    }

    @Override
    public Course saveNew(Course course) {

        var sql = "insert into courses (id, course_type, description, student_limit) values (?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                course.getId(),
                course.getCourseType().getValue(),
                course.getDescription(),
                course.getStudentLimit().getValue());

        if (response == 1)
            return course;

        return null;
    }

    @Override
    public List<Course> findAll() {

        var sql = "select * from courses c";
        return jdbcTemplate.query(sql, courseMapper);
    }

    @Override
    public Option<Course> findOne(String id) {

        var courseQuery = "select * from courses c where c.id = ?";
        var courseResult = jdbcTemplate.query(courseQuery, courseMapper, id);

        if (!courseResult.isEmpty()){

            var c = courseResult.get(0);

            var detailsQuery = "select * from course_details cd where cd.course_id = ?";
            var detailsResult = jdbcTemplate.query(detailsQuery, courseDetailsMapper, id);

            var enrolledQuery = "select students.* from students " +
                    "inner join students_courses on students.id = students_courses.student_id " +
                    "where students_courses.course_id = ?";
            var enrolledResult = jdbcTemplate.query(enrolledQuery, studentMapper, id);

            var instructorsQuery = "select instructors.* from instructors " +
                    "inner join instructors_courses on instructors.id = instructors_courses.instructor_id " +
                    "where instructors_courses.course_id = ?";
            var instructorsResult = jdbcTemplate.query(instructorsQuery, instructorMapper, id);


            for (CourseDetails cd : detailsResult) {
                c.getCourseDetails().add(cd);
            }

            for (Student s: enrolledResult) {
                c.getStudents().add(s);
            }

            for (Instructor i : instructorsResult) {
                c.getInstructors().add(i);
            }

            return Option.some(c);
        }

        return Option.none();
    }

    @Override
    public Integer deleteOne(String id) {

        String sql = "delete from courses where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Course update(Course c) {

        String sql = "update courses set course_type = ?, description = ? where id = ?";
        var response = jdbcTemplate.update(sql,
                c.getCourseType().getValue(),
                c.getDescription(),
                c.getStudentLimit().getValue(),
                c.getId());

        if (response == 1)
            return c;

        return null;
    }

    @Override
    public CourseDetails addDetails(CourseDetails cd) {

        var sql = "insert into course_details (id, course_id, weekly, day_of_week, start_time, end_time, room_id, price) values (?, ?, ?, ?, ?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                cd.getId(),
                cd.getCourseId(),
                cd.getWeekly(),
                cd.getDayOfWeek().getDay(),
                cd.getStartTime().getTime(),
                cd.getEndTime().getTime(),
                cd.getRoomId().getRoomId(),
                cd.getPrice().getAmount());

        if (response == 1)
            return cd;

        return null;
    }
}
