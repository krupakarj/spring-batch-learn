package com.example.demo.policy;

import org.springframework.batch.repeat.*;
import org.springframework.batch.repeat.policy.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Service
public class DemoCompletionPolicy {

    @Bean
    public CompletionPolicy completionPolicy() {
        CompositeCompletionPolicy policy = new CompositeCompletionPolicy();
        policy.setPolicies(
                new CompletionPolicy[] {
                        new TimeoutTerminationPolicy(1),
                        new SimpleCompletionPolicy(10)
                }
        );
        return policy;
    }
}
