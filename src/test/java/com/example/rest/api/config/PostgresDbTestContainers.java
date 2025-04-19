package com.example.rest.api.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Конфигурация для тестирования приложения с использованием контейнера базы данных PostgreSQL.
 *
 * @author ITWeiss
 */
@Testcontainers
public class PostgresDbTestContainers {

  @Container
  @ServiceConnection
  static final PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>("postgres:15-alpine");

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }
}