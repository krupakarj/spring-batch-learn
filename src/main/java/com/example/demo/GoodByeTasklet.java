package com.example.demo;

import org.springframework.batch.core.*;
import org.springframework.batch.core.scope.context.*;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.*;

public class GoodByeTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext jobContext = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext();
        System.out.println("Step2");
        String param = jobContext.getString("user.name");
        System.out.println("Step 2 output::"+param);
        return RepeatStatus.FINISHED;
    }
}
