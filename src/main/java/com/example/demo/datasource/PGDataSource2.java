package com.example.demo.datasource;

import org.springframework.boot.context.properties.*;
import org.springframework.boot.jdbc.*;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
public class PGDataSource2 {

    @Bean(name="repoDataSource")
    @ConfigurationProperties(prefix="spring.datasource2")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

}
