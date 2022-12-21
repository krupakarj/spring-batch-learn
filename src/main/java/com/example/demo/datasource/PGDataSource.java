package com.example.demo.datasource;

import org.springframework.boot.context.properties.*;
import org.springframework.boot.jdbc.*;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
public class PGDataSource {

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
