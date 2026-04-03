package com.xueyi.controller;

import com.xueyi.model.Task;
import com.xueyi.model.TaskStatus;
import com.xueyi.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setCreatedAt(LocalDateTime.now());

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }

        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskRepository.findById(id).orElseThrow();

        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());
        task.setEstimatedMinutes(updatedTask.getEstimatedMinutes());

        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}