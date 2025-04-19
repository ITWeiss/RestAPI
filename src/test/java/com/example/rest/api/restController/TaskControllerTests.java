package com.example.rest.api.restController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.rest.api.config.PostgresDbTestContainers;
import com.example.rest.api.entity.TaskEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для тестирования контроллера для работы с задачами
 *
 * @author ITWeiss
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("Тестирование контроллера для работы с задачами")
public class TaskControllerTests extends PostgresDbTestContainers {

  @Autowired
  private TaskController controller;

  @Test
  @DisplayName("Получение всех задач")
  void testGetAllTasks() {

    List<TaskEntity> tasks = controller.getAllTasks();

    assertThat(tasks).isNotNull();
    assertEquals(4, tasks.size());
  }

  @Test
  @DisplayName("Получение задачи по id")
  void testGetTaskById() {
    TaskEntity task = controller.getById(4L).getBody();

    assertThat(task).isNotNull();
    assertEquals(4, task.getId());
    assertEquals("Check style", task.getTitle());
    assertEquals("Add a maven style check", task.getDescription());
    assertEquals(true, task.getCompleted());
  }

  @Test
  @DisplayName("Создание задачи")
  void testCreateTask() {
    TaskEntity task = new TaskEntity();
    task.setTitle("Test for Controller");
    task.setDescription("Add a test for controller`s method.");
    task.setCompleted(false);

    TaskEntity createdTask = controller.create(task);

    assertThat(createdTask).isNotNull();
  }

  @Test
  @DisplayName("Полное обновление задачи")
  void testUpdateTask() {
    TaskEntity task = controller.getById(3L).getBody();
    task.setTitle("API");
    task.setDescription("Move the api to a separate class");
    task.setCompleted(true);

    ResponseEntity<TaskEntity> responseEntity = controller.update(3L, task);

    assertThat(responseEntity).isNotNull();
  }

  @Test
  @DisplayName("Частичное обновление задачи")
  void testPatchTask() {
    TaskEntity task = controller.getById(1L).getBody();
    Map<String, Object> mapForPatch = new HashMap<>();
    mapForPatch.put("completed", true);

    ResponseEntity<TaskEntity> responseEntity = controller.partialUpdate(1L, mapForPatch);

    assertThat(responseEntity).isNotNull();
    assertEquals(true, responseEntity.getBody().getCompleted());
  }

  @Test
  @DisplayName("Удаление задачи по id")
  void testDeleteTask() {
    ResponseEntity<Void> responseEntity = controller.delete(1L);

    assertThat(responseEntity).isNotNull();
    assertEquals(200, responseEntity.getStatusCodeValue());
  }


}