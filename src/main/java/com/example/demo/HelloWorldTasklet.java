package com.example.demo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.*;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.*;

public class HelloWorldTasklet implements Tasklet {
    private static final String HELLO_WORLD = "Hello, %s";
    @Override
    public RepeatStatus execute(StepContribution step, ChunkContext context)
            throws Exception {

        String name = (String)context.getStepContext()
                .getJobParameters()
                .get("name");

        ExecutionContext jobContext = context.getStepContext()
                .getStepExecution()
                //.getJobExecution()
                .getExecutionContext();

        jobContext.put("user.name", name);
        String fromContext = jobContext.getString("user.name");
        System.out.println(String.format(HELLO_WORLD, name));
        System.out.println(String.format(HELLO_WORLD, fromContext));

        return RepeatStatus.FINISHED;
    }
}
