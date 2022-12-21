package com.example.demo.config;

import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.repository.*;
import org.springframework.batch.core.repository.support.*;
import org.springframework.batch.support.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.*;
import org.springframework.transaction.*;
import org.springframework.transaction.interceptor.*;
import org.springframework.transaction.support.*;

import javax.sql.*;
import java.util.*;

@Configuration
public class CustomBatchConfigurer extends DefaultBatchConfigurer {
    @Autowired
    @Qualifier("repoDataSource")
    private DataSource dataSource;

    @Override
    protected JobRepository createJobRepository() throws Exception {
        System.out.println("Create Job Repository - Custom");
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        //factoryBean.setDatabaseType(DatabaseType.POSTGRES.toString());
        factoryBean.setTablePrefix("SPRING_");
        factoryBean.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
        factoryBean.setDataSource(this.dataSource);
        factoryBean.setTransactionManager(this.getTransactionManager());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }
}
