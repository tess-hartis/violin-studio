package com.violinstudio.scheduling.rest;

import an.awesome.pipelinr.Pipeline;
import com.violinstudio.scheduling.cqrs.instructor.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@RequestMapping("/api/v1/instructors")
public class InstructorsController {

    private final Pipeline pipeline;

    @PostMapping
    public ResponseEntity create(@RequestBody PostInstructorCmd command){

        var response = command.execute(pipeline);
        return response.fold(errors -> unprocessableEntity().body(errors),
                instructor -> ok(GetInstructorNoDetailsDto.fromDomain(instructor)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String id, @RequestBody PutInstructorCmd command){

        command.setInstructorId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                instructor -> instructor.fold(errors -> unprocessableEntity().body(errors),
                        updated -> updated.fold(() -> internalServerError().build(), ResponseEntity::ok)));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> delete(@PathVariable String id){
        var response = new DeleteInstructorCmd(id).execute(pipeline);
        return Match(response).of(
                Case($(0), notFound().build()),
                Case($(1), noContent().build()));
    }

    @RequestMapping(value = "{id}/contacts", method = RequestMethod.POST)
    public ResponseEntity addContact(@PathVariable String id, @RequestBody PostInstructorContactCmd command){

        command.setInstructorId(id);
        var response = command.execute(pipeline);
        return response.fold(() -> notFound().build(),
                instructor -> instructor.fold(errors -> unprocessableEntity().body(errors),
                        contact -> ok(GetInstructorContactDto.fromDomain(contact))));
    }

    @RequestMapping(value = "{instructorId}/courses/{courseId}", method = RequestMethod.POST)
    public ResponseEntity addCourse(@PathVariable String instructorId, @PathVariable String courseId){

        var response = new AssignToCourseCmd(instructorId, courseId).execute(pipeline);
        return response.fold(() -> notFound().build(), instructor -> ok(GetInstructorWithDetailsDto.fromDomain(instructor)));
    }

    @GetMapping
    public ResponseEntity<List<GetInstructorNoDetailsDto>> findAll(GetInstructorsQuery query){
        var response = query.execute(pipeline);
        if (response == null)
            return badRequest().build();

        return ok(response.stream().map(GetInstructorNoDetailsDto::fromDomain).collect(Collectors.toList()));
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<GetInstructorWithDetailsDto> findOne(GetOneInstructorQuery query){
        var response = query.execute(pipeline);
        return response.fold(() -> notFound().build(),
                instructor -> ok(GetInstructorWithDetailsDto.fromDomain(instructor)));
    }
}
