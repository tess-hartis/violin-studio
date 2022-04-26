package com.violinstudio.scheduling.cqrs.instructor;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.repository.instructor.InstructorsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteInstructorCmdHandler implements Command.Handler<DeleteInstructorCmd, Integer> {

    private final InstructorsRepository instructorsRepository;

    public Integer handle(DeleteInstructorCmd command){
        return instructorsRepository.deleteOne(command.id);
    }
}
