package com.example.demo.decider;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.flow.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
@Component
public class RandomDecider implements JobExecutionDecider {
    private Random random = new Random();

    public FlowExecutionStatus decide(JobExecution jobExecution,
                                      StepExecution stepExecution) {
        boolean decided = random.nextBoolean();
        if (random.nextBoolean()) {
            System.out.println("Deciding "+decided+"...");
            return new
                    FlowExecutionStatus(FlowExecutionStatus.COMPLETED.getName());
        } else {
            System.out.println("Deciding false...");
            return new
                    FlowExecutionStatus(FlowExecutionStatus.FAILED.getName());
        }
    }
}