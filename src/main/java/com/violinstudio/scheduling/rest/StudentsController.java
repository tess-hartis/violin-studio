package com.violinstudio.scheduling.rest;

import an.awesome.pipelinr.Pipeline;
import com.violinstudio.scheduling.cqrs.student.commands.*;
import com.violinstudio.scheduling.cqrs.student.queries.*;
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

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity create(@RequestBody PostStudentCmd command){

        var response = command.execute(pipeline);
        return response.fold(e -> unprocessableEntity().body(e), s -> ok(GetStudentDto.fromDomain(s)));
    }

    @GetMapping
    public ResponseEntity<List<GetStudentDto>> findAll(GetStudentsQuery query) {

        var response = query.execute(pipeline);
        if (response == null)
            return badRequest().build();

        return ok(response.stream().map(GetStudentDto::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping
    @RequestMapping({"{id}"})
    public ResponseEntity findOne(@PathVariable String id) {

        var response = new GetOneStudentQuery(id).execute(pipeline);
        return Match(response).of(
                Case($Some($()), x -> ok(GetStudentDto.fromDomain(x))),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){

        var response = new DeleteStudentCmd(id).execute(pipeline);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutStudentCmd command) {

        command.setStudentId(id);
        var student = command.execute(pipeline);
        return Match(student).of(
                Case($Some($()), y -> y.fold(e -> unprocessableEntity().body(e), s -> ok(GetStudentDto.fromDomain(s)))),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = ("{id}/primary"), method = RequestMethod.POST)
    public ResponseEntity addPrimaryContact(@PathVariable String id, @RequestBody PostPrimaryContactCmd command){
        command.setStudentId(id);
        var response = command.execute(pipeline);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), sc -> ok(GetStudentContactDto.fromDomain(sc)))),
                Case($None(), notFound().build()));

    }

    @RequestMapping(value = ("{id}/secondary"), method = RequestMethod.POST)
    public ResponseEntity addSecondaryContact(@PathVariable String id, @RequestBody PostSecondaryContactCmd command){
        command.setStudentId(id);
        var response = command.execute(pipeline);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), sc -> ok(GetStudentContactDto.fromDomain(sc)))),
                Case($None(), () -> notFound().build()));

    }

    @RequestMapping(value = ("{studentId}/courses/{courseId}"), method = RequestMethod.POST)
    public ResponseEntity addCourse(@PathVariable String studentId, @PathVariable String courseId){
        var response = new EnrollStudentCmd(studentId, courseId).execute(pipeline);
        return Match(response).of(
                Case($Some($()), x -> ok(GetStudentDto.fromDomain(x))),
                Case($None(), () -> notFound().build()));
    }

    }


