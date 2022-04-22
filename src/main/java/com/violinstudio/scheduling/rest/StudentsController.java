package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.cqrs.students.commands.*;
import com.violinstudio.scheduling.cqrs.students.queries.GetStudentContactDto;
import com.violinstudio.scheduling.cqrs.students.queries.GetStudentDto;
import com.violinstudio.scheduling.repository.CoursesRepository;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.*;
import static org.springframework.http.ResponseEntity.*;

@Component
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentsController {

    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;
    private final PostPrimaryContactHandler postPrimaryContactHandler;

    @PostMapping
    public ResponseEntity create(@RequestBody PostStudentDto dto){

        var response = dto.toDomain()
                .map(studentsRepository::saveNew).mapError(Value::toJavaList);
        return response.fold(x -> unprocessableEntity().body(x), s -> ok(GetStudentDto.fromDomain(s)));
    }

    @GetMapping
    public ResponseEntity<List<GetStudentDto>> findAll() {

        var students = studentsRepository.findAll();
        if (students == null)
            return badRequest().build();

        return ok(students.stream().map(GetStudentDto::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping
    @RequestMapping({"{id}"})
    public ResponseEntity findOne(@PathVariable String id) {

        var response = studentsRepository.findOne(id);
        return Match(response).of(
                Case($Some($()), x -> ok(GetStudentDto.fromDomain(x))),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){

        var response = studentsRepository.deleteOne(id);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutStudentDto dto) {

        var student = studentsRepository.findOne(id).map(dto::toDomain);

        return Match(student).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), s -> ok(studentsRepository.update(s)))),
                Case($None(), () -> notFound().build()));

    }

    @RequestMapping(value = ("{id}/primary"), method = RequestMethod.POST)
    public ResponseEntity addPrimaryContact(@PathVariable String id, @RequestBody PostPrimaryContactDto dto){
        dto.setStudent_id(id);
        var response = postPrimaryContactHandler.handle(dto);
        return Match(response).of(
                Case($Some($()), y -> y.fold(e -> unprocessableEntity().build(), sc -> ok(GetStudentContactDto.fromDomain(sc)))),
                Case($None(), notFound().build()));

    }

    @RequestMapping(value = ("{id}/secondary"), method = RequestMethod.POST)
    public ResponseEntity addSecondaryContact(@PathVariable String id, @RequestBody PostSecondaryContactDto dto){
        var s = studentsRepository.findOne(id);
        var response = s.map(dto::toDomain);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e.toJavaList()),
                                sc -> ok(GetStudentContactDto.fromDomain(studentsRepository.addContact(sc))))),
                Case($None(), () -> notFound().build()));

    }

    @RequestMapping(value = ("{studentId}/courses/{courseId}"), method = RequestMethod.POST)
    public ResponseEntity addCourse(@PathVariable String studentId, @PathVariable String courseId){
        var s = studentsRepository.findOne(studentId);
        var response = s.map(x -> coursesRepository.findOne(courseId)
                .map(y -> studentsRepository.addCourse(x, y)));

        return Match(response).of(
                Case($Some($()), x ->
                        x.fold(() -> new ResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.valueOf("No course found")),
                                y -> ok(GetStudentDto.fromDomain(y)))),
                Case($None(), () -> new ResponseEntity(HttpStatus.NOT_FOUND, HttpStatus.valueOf("No student found"))));

    }
    }


