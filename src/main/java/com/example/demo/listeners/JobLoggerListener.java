package com.example.demo.listeners;

import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.*;

public class JobLoggerListener {
    private static String START_MESSAGE = "%s is beginning execution";
    private static String END_MESSAGE = "%s has completed with the status %s";

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(String.format(START_MESSAGE,
                jobExecution.getJobInstance().getJobName()));
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        System.out.println(String.format(END_MESSAGE,
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus()));
    }

}