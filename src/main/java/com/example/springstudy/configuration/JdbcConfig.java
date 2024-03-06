package com.example.springstudy.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Profile("postgres")
@Configuration
@ConfigurationProperties(prefix = "db-config")
public class JdbcConfig {

    private String url;
    private String dbUser;
    private String password;
    private String driver;

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(dbUser)
                .password(password)
                .driverClassName(driver)
                .build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
