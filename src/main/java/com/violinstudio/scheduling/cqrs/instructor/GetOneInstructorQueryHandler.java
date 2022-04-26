package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetOneInstructorQueryHandler implements Command.Handler<GetOneInstructorQuery, Option<Instructor>>{

    private final InstructorsRepository instructorsRepository;

    @Override
    public Option<Instructor> handle(GetOneInstructorQuery query) {

        return instructorsRepository.findOneWithDetails(query.id);

    }
}
