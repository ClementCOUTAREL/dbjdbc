package com.coutarel.dbpostgres.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

  @Bean
  public JdbcTemplate restTemplate(final DataSource dataSource){
    return new JdbcTemplate(dataSource);
  }
}