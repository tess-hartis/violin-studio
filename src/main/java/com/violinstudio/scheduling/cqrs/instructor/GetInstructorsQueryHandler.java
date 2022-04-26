package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.domain.instructor.Instructor;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GetInstructorsQueryHandler implements Command.Handler<GetInstructorsQuery, List<Instructor>>{

    private final InstructorsRepository instructorsRepository;

    @Override
    public List<Instructor> handle(GetInstructorsQuery getInstructorsQuery) {
        return instructorsRepository.findAll();
    }
}
