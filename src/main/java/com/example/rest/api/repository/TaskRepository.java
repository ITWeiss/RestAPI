package com.example.rest.api.repository;

import com.example.rest.api.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с задачами
 *
 * @author ITWeiss
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}