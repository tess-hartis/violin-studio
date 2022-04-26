package com.violinstudio.scheduling.repository.student;

import com.violinstudio.scheduling.domain.course.Course;
import com.violinstudio.scheduling.domain.student.Student;
import com.violinstudio.scheduling.domain.student.StudentContact;
import com.violinstudio.scheduling.repository.course.CourseMapper;
import io.vavr.control.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public class StudentsRepositoryImpl implements StudentsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;
    private final StudentContactMapper studentContactMapper;
    private final CourseMapper courseMapper;

    public StudentsRepositoryImpl(JdbcTemplate jdbcTemplate, StudentMapper studentMapper, StudentContactMapper studentContactMapper, CourseMapper courseMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
        this.studentContactMapper = studentContactMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public Student saveNew(Student student){

        var sql = "insert into students (first_name, last_name, birthday, instruments, date_enrolled, id) values (?, ?, ?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                student.getStudentName().getFirstName(),
                student.getStudentName().getLastName(),
                student.getBirthday().getBirthday(),
                student.getInstruments().getInstruments(),
                student.getDateEnrolled(),
                student.getId());

        if (response == 1)
            return student;

        return null;
    }

    @Override
    public List<Student> findAll() {

        String sql = "select * from students s ";
        return jdbcTemplate.query(sql, studentMapper);
    }

    @Override
    public Option<Student> findOne(String id){

        var studentQuery = "select * from students s where s.id = ?";
        var studentResult = jdbcTemplate.query(studentQuery, new Object[] {id}, studentMapper);

        if (!studentResult.isEmpty()){

            var student = studentResult.get(0);

            var contactsQuery = "select * from student_contacts s where s.student_id = ?";
            var contactsResult = jdbcTemplate.query(contactsQuery, new Object [] {id}, studentContactMapper);

            var coursesQuery = "select courses.* from courses " +
                    "inner join students_courses on courses.id = students_courses.course_id " +
                    "where students_courses.student_id = ?";
            var coursesResult = jdbcTemplate.query(coursesQuery, new Object[] {id}, courseMapper);

            for (StudentContact sc : contactsResult) {
                student.getContactInfo().add(sc);
            }

            for (Course c: coursesResult) {
                student.getEnrolledIn().add(c);
            }

            return Option.some(student);
        }

        return Option.none();
    }

    @Override
    public Integer deleteOne(String id) {

        var sql = "delete from students where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Student update(Student student) {

        var sql = "update students set first_name = ?, last_name = ?, birthday = ?, instruments = ? where id = ?";
        var response = jdbcTemplate.update(sql,
                student.getStudentName().getFirstName(),
                student.getStudentName().getLastName(),
                student.getBirthday().getBirthday(),
                student.getInstruments().getInstruments(),
                student.getId());

        if (response == 1)
            return student;

        return null;
    }

    @Override
    public StudentContact addContact(StudentContact sc) {

        var sql = "insert into student_contacts (id, contact_type, first_name, last_name, email, phone, student_id) values (?, ?, ?, ?, ?, ?, ?)";
        var response = jdbcTemplate.update(sql,
                sc.getId(), sc.getContactType().getType(),
                sc.getName().getFirstName(),
                sc.getName().getLastName(),
                sc.getEmail().getValue(),
                sc.getPhone().getValue(),
                sc.getStudentId());

        if (response == 1)
            return sc;

        return null;
    }

    @Override
    public Student addCourse(Student student, Course course){

        var sql = "insert into students_courses(student_id, course_id) values (?, ?)";
        var response = jdbcTemplate.update(sql,
                student.getId(),
                course.getId());

        if (response == 1)
           return student;

        return null;

    }
}
