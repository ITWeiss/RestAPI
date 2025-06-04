package com.example.rest.api.restController;

import static com.example.rest.api.config.API.TASKS;
import static com.example.rest.api.config.API.TASKS_BY_ID;

import com.example.rest.api.entity.TaskEntity;
import com.example.rest.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для работы с задачами{@link TaskEntity}
 *
 * @author ITWeiss
 */
@RestController
@RequestMapping(TASKS)
@RequiredArgsConstructor
public class TaskController {

  private final TaskRepository repository;

  /**
   * Получение всех задач
   *
   * @return список задач
   */
  @GetMapping
  public List<TaskEntity> getAllTasks() {
    return repository.findAll();
  }


  /**
   * Получение задачи по id
   *
   * @param id идентификатор задачи
   * @return сущность задачи
   */
  @GetMapping(TASKS_BY_ID)
  public ResponseEntity<TaskEntity> getById(@PathVariable("id") Long id) {
    return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Создание задачи
   *
   * @param task сущность задачи
   * @return сущность задачи
   */
  @PostMapping
  public TaskEntity create(@RequestBody TaskEntity task) {
    return repository.save(task);
  }

  /**
   * Полное обновление задачи
   *
   * @param id            идентификатор задачи
   * @param taskForUpdate сущность задачи
   * @return сущность задачи
   */
  @PutMapping(TASKS_BY_ID)
  public ResponseEntity<TaskEntity> update(@PathVariable("id") Long id, @RequestBody TaskEntity taskForUpdate) {
    return repository.findById(id)
        .map(task -> {
          task.setTitle(taskForUpdate.getTitle());
          task.setDescription(taskForUpdate.getDescription());
          task.setCompleted(taskForUpdate.getCompleted());
          return ResponseEntity.ok(repository.save(task));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Частичное обновление задачи
   *
   * @param id          идентификатор задачи
   * @param mapForPatch список полей для частичного обновления
   * @return сущность задачи
   */
  @PatchMapping(TASKS_BY_ID)
  public ResponseEntity<TaskEntity> partialUpdate(@PathVariable("id") Long id, @RequestBody Map<String, Object> mapForPatch) {
    return repository.findById(id)
        .map(task -> {
          mapForPatch.forEach((key, value) -> {
            switch (key) {
              case "title" -> task.setTitle((String) value);
              case "description" -> task.setDescription((String) value);
              case "completed" -> task.setCompleted((Boolean) value);
              default -> throw new IllegalArgumentException("Недопустимое поле для обновления");
            }
          });
          return ResponseEntity.ok(repository.save(task));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Удаление задачи по id
   *
   * @param id идентификатор задачи
   * @return статус ответа
   */
  @DeleteMapping(TASKS_BY_ID)
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }
}