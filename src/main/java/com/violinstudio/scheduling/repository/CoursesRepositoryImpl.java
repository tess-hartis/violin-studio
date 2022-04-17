package com.violinstudio.scheduling.repository;

import com.violinstudio.scheduling.domain.Course;
import com.violinstudio.scheduling.domain.CourseDetails;
import com.violinstudio.scheduling.rest.CourseDetailsMapper;
import com.violinstudio.scheduling.rest.CourseMapper;
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

    public CoursesRepositoryImpl(JdbcTemplate jdbcTemplate, CourseMapper courseMapper, CourseDetailsMapper courseDetailsMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseMapper = courseMapper;
        this.courseDetailsMapper = courseDetailsMapper;
    }

    @Override
    public Course saveNew(Course course) {

        var response = jdbcTemplate.update("insert into courses" +
                "(id, course_type, description, student_limit) values (?, ?, ?, ?)",
                course.getId(), course.getCourseType().getValue(), course.getDescription(),
                course.getStudentLimit().getValue());

        if (response == 1)
            return course;

        return null;
    }

    @Override
    public List<Course> findAll() {

        String sql = "select * from courses c";
        return jdbcTemplate.query(sql, courseMapper);
    }

    @Override
    public Option<Course> findOne(String id) {

        String courseQuery = "select * from courses c where c.id = ?";
        var courseResult = jdbcTemplate.query(courseQuery, new Object[] {id}, courseMapper);

        if (!courseResult.isEmpty()){

            var c = courseResult.get(0);
            String detailsQuery = "select * from course_details cd where cd.course_id = ?";
            var detailsResult = jdbcTemplate.query(detailsQuery, new Object[] {id}, courseDetailsMapper);

            for (CourseDetails cd : detailsResult) {
                c.getCourseDetails().add(cd);
            }
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
        var response = jdbcTemplate.update(sql, c.getCourseType().getValue(), c.getDescription(),
                c.getStudentLimit().getValue(), c.getId());

        if (response == 1)
            return c;

        return null;
    }

    @Override
    public CourseDetails addDetails(CourseDetails cd) {
        var response = jdbcTemplate.update("insert into course_details" +
                "(id, course_id, weekly, day_of_week, start_time, end_time, room_id, price)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?)", cd.getId(), cd.getCourseId(), cd.getWeekly(),
                cd.getDayOfWeek().getDay(), cd.getStartTime().getTime(), cd.getEndTime().getTime(),
                cd.getRoomId().getRoomId(), cd.getPrice().getAmount());

        if (response == 1)
            return cd;

        return null;
    }
}
