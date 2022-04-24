package com.violinstudio;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.violinstudio.scheduling.cqrs.student.commands.PostStudentCmdHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Configuration
@Component
public class PipelinrConfiguration {

    @Bean
    public Pipeline pipeline(ObjectProvider<Command.Handler> handlers){
        return new Pipelinr()
                .with(handlers::stream);
    }
}
