package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.repository.CoursesRepository;
import io.vavr.Value;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

@Component
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/courses")
public class CoursesController {

    private final CoursesRepository coursesRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody PostCourseDto dto){
        var response = dto.toDomain()
                .map(coursesRepository::saveNew).mapError(Value::toJavaList);
        return response.fold(x -> unprocessableEntity().body(x), c -> ok(GetOneCourseDto.fromDomain(c)));
    }

    @GetMapping
    public ResponseEntity<List<GetAllCoursesDto>> findAll(){
        var courses = coursesRepository.findAll();
        if (courses == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ok(courses.stream().map(GetAllCoursesDto::fromDomain).collect(Collectors.toList()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<GetOneCourseDto> findOne(@PathVariable String id){
        var response = coursesRepository.findOne(id);
        return Match(response).of(
                Case($Some($()), x -> new ResponseEntity<>(GetOneCourseDto.fromDomain(x), HttpStatus.OK)),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        var response = coursesRepository.deleteOne(id);
        return Match(response).of(
                Case($(0), new ResponseEntity<>(HttpStatus.NOT_FOUND)),
                Case($(1), new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutCourseDto dto){
        var response = coursesRepository.findOne(id).map(dto::toDomain);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e),
                                s -> ok(GetOneCourseDto.fromDomain(coursesRepository.update(s))))),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @RequestMapping(value = ("{id}/details"), method = RequestMethod.POST)
    public ResponseEntity addDetails(@PathVariable String id, @RequestBody PostCourseDetailsDto dto){
        var response = coursesRepository.findOne(id).map(dto::toDomain);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e),
                                d -> ok(GetCourseDetailsDto.fromDomain(coursesRepository.addDetails(d))))),
                Case($None(), () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }



}
