package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.domain.StudentContact;
import com.violinstudio.scheduling.repository.StudentsRepository;
import io.vavr.Value;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.*;
import static org.springframework.http.ResponseEntity.*;
import static io.vavr.Predicates.*;

@Component
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentsController {

    private final StudentsRepository studentsRepository;

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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ok(students.stream().map(GetStudentDto::fromDomain).collect(Collectors.toList()));
    }

    @GetMapping
    @RequestMapping({"{id}"})
    public ResponseEntity findOne(@PathVariable String id) {

        var response = studentsRepository.findOne(id);
        return Match(response).of(
                Case($Some($()), x -> new ResponseEntity<>(GetStudentDto.fromDomain(x), HttpStatus.OK)),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){

        var response = studentsRepository.deleteOne(id);
        return Match(response).of(
                Case($(0), new ResponseEntity<>(HttpStatus.NOT_FOUND)),
                Case($(1), new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutStudentDto dto) {

        var student = studentsRepository.findOne(id).map(dto::toDomain);

        return Match(student).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), s -> ok(studentsRepository.update(s)))),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));

//        return x.fold(ResponseEntity.notFound(), c -> c.fold(b -> ResponseEntity.badRequest(), g -> ok(g)));


//        return x.fold(notFound(), s -> s.fold(e -> ok(), gs -> ok(studentsRepository.update(gs))));

//        return Match(x).of(
//                Case($Some($()), y ->
//                        Match(y).of(
//                                Case($Valid($()), s -> ok(studentsRepository.update(s))  ),
//                                Case($Invalid($()), e -> new ResponseEntity<>(e.toJavaList(), HttpStatus.UNPROCESSABLE_ENTITY))
//                        )),
//                Case($None(), () -> notFound())
//        );



//        return Match(response).of(
//                Case($Some($()), x ->
//                        x.fold(e -> unprocessableEntity().body(e), s -> ok(GetStudentDto.fromDomain(s)))),
//                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }

    @RequestMapping(value = ("{id}/contacts"), method = RequestMethod.POST)
    public ResponseEntity addContact(@PathVariable String id, @RequestBody PostStudentContactDto dto){
        var response = studentsRepository.findOne(id).map(dto::toDomain);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e),
                                s -> ok(GetStudentContactDto.fromDomain(studentsRepository.addContact(s))))),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));

    }
    }


