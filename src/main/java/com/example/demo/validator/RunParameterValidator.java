package com.example.demo.validator;

import org.springframework.batch.core.*;
import org.springframework.batch.core.job.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component
public class RunParameterValidator {

    @Bean
    public JobParametersValidator validator() {
        DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
        validator.setRequiredKeys(new String[] {});
        validator.setOptionalKeys(new String[] {"name","currentDate"});
        validator.afterPropertiesSet();
        return validator;
    }
}
