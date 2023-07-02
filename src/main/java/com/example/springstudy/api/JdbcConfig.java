package com.example.springstudy.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "db-config")
public class JdbcConfig {

    private String url;
    private String dbUser;
    private String password;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder
                .url(url)
                .username(dbUser)
                .password(password)
                .driverClassName("org.postgresql.Driver")
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
}
