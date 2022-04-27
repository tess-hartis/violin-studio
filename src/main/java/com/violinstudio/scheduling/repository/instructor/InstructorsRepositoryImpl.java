package com.violinstudio.scheduling.repository.instructor;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.domain.instructor.InstructorContact;
import com.violinstudio.scheduling.repository.course.CourseMapper;
import io.vavr.control.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Component
public class InstructorsRepositoryImpl implements InstructorsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final InstructorMapper instructorMapper;
    private final InstructorContactMapper instructorContactMapper;
    private final CourseMapper courseMapper;

    public InstructorsRepositoryImpl(JdbcTemplate jdbcTemplate, InstructorMapper instructorMapper,
                                     InstructorContactMapper instructorContactMapper, CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.instructorMapper = instructorMapper;
        this.instructorContactMapper = instructorContactMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public Instructor saveNew(Instructor instructor) {
        var sql = "insert into instructors (first_name, last_name, bio, date_added, id) values (?, ?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                instructor.getName().getFirstName(),
                instructor.getName().getLastName(),
                instructor.getBio().getValue(),
                instructor.getDateAdded(),
                instructor.getId());

        if (response == 1)
            return instructor;

        return null;
    }

    @Override
    public List<Instructor> findAll() {

        String sql = "select * from instructors i";
        return jdbcTemplate.query(sql, instructorMapper);
    }

    @Override
    public Option<Instructor> findOneWithDetails(String id) {

        var instructorQuery = "select * from instructors i where i.id = ?";
        var instructorResult = jdbcTemplate.query(instructorQuery, new Object[] {id}, instructorMapper );

        if (!instructorResult.isEmpty()){

            var instructor = instructorResult.get(0);

            var contactsQuery = "select * from instructor_contacts i where i.instructor_id = ?";
            var contactsResult = jdbcTemplate.query(contactsQuery, instructorContactMapper, id);

            var coursesQuery = "select courses.* from courses " +
                    "inner join instructors_courses on courses.id = instructors_courses.course_id " +
                    "where instructors_courses.instructor_id = ?";
            var coursesResult = jdbcTemplate.query(coursesQuery, courseMapper, id);

            for (InstructorContact ic : contactsResult) {
                instructor.getContacts().add(ic);
            }

            for (Course c : coursesResult) {
                instructor.getClasses().add(c);
            }

            return Option.some(instructor);
        }

        return Option.none();
    }

    @Override
    public Option<Instructor> findOne(String id) {

        var instructorQuery = "select * from instructors i where i.id = ?";
        var instructorResult = jdbcTemplate.query(instructorQuery, new Object[] {id}, instructorMapper );

        if (!instructorResult.isEmpty())
            return Option.some(instructorResult.get(0));

        return Option.none();
    }

    @Override
    public Integer deleteOne(String id) {

        var sql = "delete from instructors where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Option<Instructor> update(Instructor instructor) {

        var sql = "update instructors set first_name = ?, last_name = ?, bio = ? where id = ?";
        var response = jdbcTemplate.update(sql,
                instructor.getName().getFirstName(),
                instructor.getName().getLastName(),
                instructor.getBio().getValue());

       if (response == 1)
           return Option.some(instructor);

       return Option.none();
    }

    @Override
    public InstructorContact addContact(InstructorContact i) {

        var sql = "insert into instructor_contacts" +
                "(id, street_address, city, state, zipcode, email, phone, instructor_id)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                i.getId(),
                i.getAddress().getStreetAddress(),
                i.getAddress().getCity(),
                i.getAddress().getState(),
                i.getAddress().getZipcode(),
                i.getEmail().getValue(),
                i.getPhone().getValue(),
                i.getInstructorId());

        if (response == 1)
            return i;

        return null;
    }

    @Override
    public Instructor addCourse(Instructor instructor, Course course) {

        var sql = "insert into instructors_courses (instructor_id, course_id) values (?, ?)";
        var response = jdbcTemplate.update(sql,
                instructor.getId(),
                course.getId());

        if (response == 1)
            return instructor;

        return null;
    }
}
