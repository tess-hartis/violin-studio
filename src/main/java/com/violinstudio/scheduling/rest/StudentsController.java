package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.cqrs.students.commands.*;
import com.violinstudio.scheduling.cqrs.students.queries.GetAllStudentsQuery;
import com.violinstudio.scheduling.cqrs.students.queries.GetStudentContactDto;
import com.violinstudio.scheduling.cqrs.students.queries.GetStudentDto;
import com.violinstudio.scheduling.repository.CoursesRepository;
import com.violinstudio.scheduling.repository.StudentsRepository;
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
    private final PostPrimaryContactCommand postPrimaryContactCommand;
    private final PostStudentCommand postStudentHandler;
    private final PutStudentCommand putStudentCommand;
    private final GetAllStudentsQuery getAllStudentsQuery;
    private final PostSecondaryContactCommand postSecondaryContactCommand;
    private final EnrollStudentCommand enrollStudentCommand;

    @PostMapping
    public ResponseEntity create(@RequestBody PostStudentDto dto){

        var response = postStudentHandler.handle(dto);
        return response.fold(e -> unprocessableEntity().body(e), s -> ok(GetStudentDto.fromDomain(s)));
    }

    @GetMapping
    public ResponseEntity<List<GetStudentDto>> findAll() {

        var students = getAllStudentsQuery.handle();
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

        dto.setStudentId(id);
        var student = putStudentCommand.handle(dto);

        return Match(student).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), ResponseEntity::ok)),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = ("{id}/primary"), method = RequestMethod.POST)
    public ResponseEntity addPrimaryContact(@PathVariable String id, @RequestBody PostPrimaryContactDto dto){
        dto.setStudent_id(id);
        var response = postPrimaryContactCommand.handle(dto);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), sc -> ok(GetStudentContactDto.fromDomain(sc)))),
                Case($None(), notFound().build()));

    }

    @RequestMapping(value = ("{id}/secondary"), method = RequestMethod.POST)
    public ResponseEntity addSecondaryContact(@PathVariable String id, @RequestBody PostSecondaryContactDto dto){
        dto.setStudent_id(id);
        var response = postSecondaryContactCommand.handle(dto);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), sc -> ok(GetStudentContactDto.fromDomain(sc)))),
                Case($None(), () -> notFound().build()));

    }

    @RequestMapping(value = ("{studentId}/courses/{courseId}"), method = RequestMethod.POST)
    public ResponseEntity addCourse(@PathVariable String studentId, @PathVariable String courseId){
        var response = enrollStudentCommand.handle(studentId, courseId);
        return Match(response).of(
                Case($Some($()), x -> ok(GetStudentDto.fromDomain(x))),
                Case($None(), () -> notFound().build()));
    }
    }


