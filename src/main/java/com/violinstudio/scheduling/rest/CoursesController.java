package com.violinstudio.scheduling.rest;

import com.violinstudio.scheduling.cqrs.course.commands.*;
import com.violinstudio.scheduling.cqrs.course.queries.GetCourseNoDetailsDto;
import com.violinstudio.scheduling.cqrs.course.queries.GetAllCoursesQuery;
import com.violinstudio.scheduling.cqrs.course.queries.GetCourseDetailsDto;
import com.violinstudio.scheduling.cqrs.course.queries.GetOneCourseQuery;
import com.violinstudio.scheduling.cqrs.course.queries.GetCourseWithDetailsDto;
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
import static org.springframework.http.ResponseEntity.*;

@Component
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/courses")
public class CoursesController {

    private final PostCourseCommand postCourseCommand;
    private final GetAllCoursesQuery getAllCoursesQuery;
    private final GetOneCourseQuery getOneCourseQuery;
    private final PutCourseCommand putCourseCommand;
    private final PostCourseDetailsCommand postCourseDetailsCommand;
    private final DeleteCourseCommand deleteCourseCommand;

    @PostMapping
    public ResponseEntity create(@RequestBody PostCourseDto dto){
        return postCourseCommand.handle(dto)
                .fold(x -> unprocessableEntity().body(x), c -> ok(GetCourseWithDetailsDto.fromDomain(c)));
    }

    @GetMapping
    public ResponseEntity<List<GetCourseNoDetailsDto>> findAll(){
        var courses = getAllCoursesQuery.handle();
        if (courses == null)
            return badRequest().build();

        return ok(courses.stream().map(GetCourseNoDetailsDto::fromDomain).collect(Collectors.toList()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<GetCourseWithDetailsDto> findOne(@PathVariable String id){
        var response = getOneCourseQuery.handle(id);
        return Match(response).of(
                Case($Some($()), x -> ok(GetCourseWithDetailsDto.fromDomain(x))),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        var response = deleteCourseCommand.handle(id);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutCourseDto dto){
        dto.setCourseId(id);
        var response = putCourseCommand.handle(dto);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), s -> ok(GetCourseWithDetailsDto.fromDomain(s)))),
                Case($None(), () -> notFound().build()));
    }

    @RequestMapping(value = ("{id}/details"), method = RequestMethod.POST)
    public ResponseEntity addDetails(@PathVariable String id, @RequestBody PostCourseDetailsDto dto){
        dto.setCourseId(id);
        var response = postCourseDetailsCommand.handle(dto);
        return Match(response).of(
                Case($Some($()), y ->
                        y.fold(e -> unprocessableEntity().body(e), cd -> ok(GetCourseDetailsDto.fromDomain(cd)))),
                Case($None(), () -> notFound().build()));
    }



}
