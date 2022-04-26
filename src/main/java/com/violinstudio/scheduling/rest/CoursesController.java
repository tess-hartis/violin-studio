package com.violinstudio.scheduling.rest;

import an.awesome.pipelinr.Pipeline;
import com.violinstudio.scheduling.cqrs.course.commands.*;
import com.violinstudio.scheduling.cqrs.course.queries.*;
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
@RequestMapping("/api/v1/courses")
public class CoursesController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity create(@RequestBody PostCourseCmd command){

        var response = command.execute(pipeline);
        return response.fold(errors -> unprocessableEntity().body(errors), c -> ok(GetCourseWithDetailsDto.fromDomain(c)));
    }

    @GetMapping
    public ResponseEntity<List<GetCourseNoDetailsDto>> findAll(GetAllCoursesQuery query){

        var courses = query.execute(pipeline);
        if (courses == null)
            return badRequest().build();

        return ok(courses.stream().map(GetCourseNoDetailsDto::fromDomain).collect(Collectors.toList()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<GetCourseWithDetailsDto> findOne(@PathVariable String id){

        var response = new GetOneCourseQuery(id).execute(pipeline);
        return response.fold(() -> notFound().build(), course -> ok(GetCourseWithDetailsDto.fromDomain(course)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){

        var response = new DeleteCourseCmd(id).execute(pipeline);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = ("{id}"), method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutCourseCmd command){

        command.setCourseId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                course -> course.fold(errors -> unprocessableEntity().body(errors),
                        updated -> ok(GetCourseWithDetailsDto.fromDomain(updated))));
    }

    @RequestMapping(value = ("{id}/details"), method = RequestMethod.POST)
    public ResponseEntity addDetails(@PathVariable String id, @RequestBody PostCourseDetailsCmd command){

        command.setCourseId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                course -> course.fold(errors -> unprocessableEntity().body(errors),
                        details -> ok(GetCourseDetailsDto.fromDomain(details))));
    }



}
