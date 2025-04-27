package com.uts.casee.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uts.casee.task.model.Task;
import com.uts.casee.task.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
     private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // Endpoint untuk mengambil semua Task
    @GetMapping
    public List<Task> getAllTasks() {
        log.info("GET /api/tasks accessed");
        return taskService.getAll();
    }
    
    // Endpoint untuk mengambil Task berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        log.info("GET /api/tasks/{} accessed", id);
            return taskService.getById(id)
                .map(task -> ResponseEntity.ok().body(task))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint membuat Task baru
    @PostMapping
    public Task  createTask(@RequestBody Task task) {
        log.info("POST /api/tasks accessed with body: {}", task);
            return taskService.createTask(task);
    }

    // Endpoint untuk update Task yang sudah ada
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        log.info("PUT /api/tasks/{} accessed with body: {}", id, taskDetails);

        try {
            Task updateTask = taskService.updateTask(id, taskDetails);
            return ResponseEntity.ok(updateTask);
        } catch (RuntimeException e) {
            log.warn("PUT /api/tasks/{} failed: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE Task
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
    log.info("DELETE /api/tasks/{} accessed", id);
    Map<String, String> response = new HashMap<>();
        try {
            taskService.deleteTask(id);
            response.put("message", "Task berhasil dihapus");
            return ResponseEntity.ok("Task deleted successfully");
        } catch (RuntimeException e) {
            response.put("message", "Task tidak ditemukan dengan id" + id);
            log.warn("DELETE /api/tasks/{} failed: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
