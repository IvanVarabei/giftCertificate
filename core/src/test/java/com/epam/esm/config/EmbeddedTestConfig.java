package com.epam.esm.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan
@Profile("dev")
public class EmbeddedTestConfig {
    @Bean
    public DataSource createDataSource() throws IOException {
        EmbeddedPostgres postgres = EmbeddedPostgres.start();
        return postgres.getPostgresDatabase();
    }
}