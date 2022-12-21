package com.example.demo;

import org.springframework.batch.core.*;

import java.util.*;

public class DailyJobTimestamper implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParameters params = new JobParameters();
        return new JobParametersBuilder(params)
                .addDate("currentDate", new Date())
                .toJobParameters();
    }

}