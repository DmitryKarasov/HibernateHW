package com.karasov.hibernatehw.config;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@RequiredArgsConstructor
@DependsOn("entityManagerFactory")
public class LiquibaseConfig {

    private final DataSource dataSource;

    @Bean
    public Liquibase liquibase() throws SQLException, LiquibaseException {
        return new Liquibase(
                "classpath:db/changelog/db.changelog-master.xml",
                new ClassLoaderResourceAccessor(),
                new JdbcConnection(dataSource.getConnection()));
    }
}
