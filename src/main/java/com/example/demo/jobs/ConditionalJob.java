package com.example.demo.jobs;

import com.example.demo.*;
import com.example.demo.decider.*;
import com.example.demo.validator.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.flow.*;
import org.springframework.batch.core.step.tasklet.*;
import org.springframework.batch.repeat.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.*;

@EnableBatchProcessing
@ComponentScan(basePackages = "com.example.demo")
public class ConditionalJob {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RunParameterValidator pval;

    @Autowired
    private RandomDecider decider;

    @Bean
    public Tasklet passTasklet() {
        return (stepContribution, chunkContext) -> {
            //System.out.println("Just Passing...");
            return RepeatStatus.FINISHED;
            //throw new RuntimeException("Fail");
        };
    }
    @Bean
    public Tasklet successTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Success!!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet failTasklet() {
        return (stepContribution, chunkContext) -> {
            System.out.println("Failed!!");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step firstStep() {
        //System.out.println("*** First step");
        return stepBuilderFactory.get("firstStep")
                .tasklet(passTasklet())
                .build();
    }

    @Bean
    public Step successStep() {
        return stepBuilderFactory.get("successStep")
                .tasklet(successTasklet())
                .build();
    }

    @Bean
    public Step failureStep() {
        return stepBuilderFactory.get("failureStep")
                .tasklet(failTasklet())
                .build();
    }

    @Bean
    public Job job() {
        System.out.println("conditionalJob");
        return this.jobBuilderFactory.get("conditionalJob")
                .validator(pval.validator())
                .incrementer(new DailyJobTimestamper())
                .start(firstStep())
                //.next(decider)
                //.from(decider)
                .on("FAILED").to(failureStep())
                .from(firstStep())
                //.from(decider)
                .on("COMPLETED").to(successStep())
                .end()
                .build();
    }

}
