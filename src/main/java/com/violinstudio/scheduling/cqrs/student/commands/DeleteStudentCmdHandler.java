package com.violinstudio.scheduling.cqrs.student.commands;

import an.awesome.pipelinr.Command;
import com.violinstudio.scheduling.repository.student.StudentsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DeleteStudentCmdHandler implements Command.Handler<DeleteStudentCmd, Integer> {

    private final StudentsRepository studentsRepository;

    public Integer handle(DeleteStudentCmd command){
        return studentsRepository.deleteOne(command.id);
    }
}
