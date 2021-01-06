package com.epam.esm.config;

import com.epam.esm.exception.PropertiesFileReadingException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
public class ModelSpringConfig {
    private static final Logger log = LogManager.getLogger(ModelSpringConfig.class);

    @Bean
    public DataSource getDataSource() {
        Properties properties = new Properties();
        //todo use speshial annat
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            log.fatal(e);
            throw new PropertiesFileReadingException(e);
        }
        HikariConfig config = new HikariConfig(properties);
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    @Bean
    public DataSourceTransactionManager createTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(getDataSource());
        return transactionManager;
    }

//    @Bean
//    public GiftCertificateRepo repo() {
//        return new GiftCertificateRepo(getJdbcTemplate());
//    }
}
