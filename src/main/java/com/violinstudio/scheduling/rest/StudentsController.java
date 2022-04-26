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
        return response.fold(e -> unprocessableEntity().body(e), s -> ok(GetStudentWithDetailsDto.fromDomain(s)));
    }

    @GetMapping
    public ResponseEntity<List<GetStudentWithDetailsDto>> findAll(GetStudentsQuery query) {

        var response = query.execute(pipeline);
        if (response == null)
            return badRequest().build();

        return ok(response.stream().map(GetStudentWithDetailsDto::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping
    @RequestMapping({"{id}"})
    public ResponseEntity findOne(@PathVariable String id) {

        var response = new GetOneStudentQuery(id).execute(pipeline);
        return response.fold(() -> notFound().build(), student -> ok(GetStudentWithDetailsDto.fromDomain(student)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){

        var response = new DeleteStudentCmd(id).execute(pipeline);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutStudentCmd command) {

        command.setStudentId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                student -> student.fold(errors -> unprocessableEntity().body(errors),
                        updated -> ok(GetStudentWithDetailsDto.fromDomain(updated))));

    }

    @RequestMapping(value = ("{id}/primary"), method = RequestMethod.POST)
    public ResponseEntity addPrimaryContact(@PathVariable String id, @RequestBody PostPrimaryContactCmd command){

        command.setStudentId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                student -> student.fold(errors -> unprocessableEntity().body(errors),
                        contact -> ok(GetStudentContactDto.fromDomain(contact))));

    }

    @RequestMapping(value = ("{id}/secondary"), method = RequestMethod.POST)
    public ResponseEntity addSecondaryContact(@PathVariable String id, @RequestBody PostSecondaryContactCmd command){

        command.setStudentId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                student -> student.fold(errors -> unprocessableEntity().body(errors),
                        contact -> ok(GetStudentContactDto.fromDomain(contact))));

    }

    @RequestMapping(value = ("{studentId}/courses/{courseId}"), method = RequestMethod.POST)
    public ResponseEntity addCourse(@PathVariable String studentId, @PathVariable String courseId){

        var response = new EnrollStudentCmd(studentId, courseId).execute(pipeline);
        return response.fold(() -> notFound().build(), student -> ok(GetStudentWithDetailsDto.fromDomain(student)));
    }

    }


