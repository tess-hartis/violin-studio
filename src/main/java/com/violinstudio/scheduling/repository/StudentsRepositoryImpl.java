package com.violinstudio.scheduling.repository;

import com.violinstudio.scheduling.domain.*;
import com.violinstudio.scheduling.rest.StudentContactMapper;
import com.violinstudio.scheduling.rest.StudentMapper;
import io.vavr.control.Option;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Component
public class StudentsRepositoryImpl implements StudentsRepository{

    private final JdbcTemplate jdbcTemplate;
    private final StudentMapper studentMapper;
    private final StudentContactMapper studentContactMapper;



    public StudentsRepositoryImpl(JdbcTemplate jdbcTemplate, StudentMapper studentMapper, StudentContactMapper studentContactMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
        this.studentContactMapper = studentContactMapper;
    }

    @Override
    public Student saveNew(Student student){
        var response = jdbcTemplate.update("insert into students " +
                "(first_name, last_name, birthday, instruments," +
                "date_enrolled, id) values (?, ?, ?, ?, ?, ?)",
                student.getStudentName().getFirstName(), student.getStudentName().getLastName(),
                student.getBirthday().getBirthday(), student.getInstruments().getInstruments(),
                student.getDateEnrolled(), student.getId());

        if (response == 1)
            return student;

        return null;
    }

    @Override
    public List<Student> findAll() {

        String sql = "select * from students s ";
        return jdbcTemplate.query(sql, studentMapper);
//
//        String contactQuery = "";
//        List<StudentContact> contacts = jdbcTemplate.query(contactQuery, new StudentContactMapper());
//
//        var response = students.withContactInfo(contacts);
//
//        students.stream().flatMap(s -> contacts.stream().filter(c -> s.getId() == c.getStudentId() ));
    }

    @Override
    public Option<Student> findOne(String id){

        String studentQuery = "select * from students s where s.id = ?";
        var studentResult = jdbcTemplate.query(studentQuery, new Object[] {id}, studentMapper);

        if (!studentResult.isEmpty()){

            String contactsQuery = "select * from student_contacts s where s.student_id = ?";
            var contactsResult = jdbcTemplate.query(contactsQuery, new Object [] {id}, studentContactMapper);
            var student = studentResult.get(0);

            for (StudentContact sc : contactsResult) {
                student.getContactInfo().add(sc);
            }
            return Option.some(student);
        }

        return Option.none();
    }

    @Override
    public Integer deleteOne(String id) {

        String sql = "delete from students where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public Student update(Student student) {

        String sql = "update students set first_name = ?, last_name = ?, birthday = ?, instruments = ? where id = ?";
        var response = jdbcTemplate.update(sql, student.getStudentName().getFirstName(), student.getStudentName().getLastName(),
                student.getBirthday().getBirthday(), student.getInstruments().getInstruments(), student.getId());

        if (response == 1)
            return student;

        return null;
    }

    @Override
    public StudentContact addContact(StudentContact sc) {
        jdbcTemplate.update("insert into student_contacts" +
                "(id, primary_contact, first_name, last_name, email, phone, student_id)" +
                "values (?, ?, ?, ?, ?, ?, ?)", sc.getId(), sc.getPrimaryContact(), sc.getName().getFirstName(),
                sc.getName().getLastName(), sc.getEmail().getValue(), sc.getPhone().getValue(), sc.getStudentId());

        return sc;
    }


}
