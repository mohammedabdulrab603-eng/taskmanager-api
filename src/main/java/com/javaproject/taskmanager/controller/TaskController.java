package com.javaproject.taskmanager.controller;

import com.javaproject.taskmanager.entity.Task;
import com.javaproject.taskmanager.repository.TaskRepository;
import com.javaproject.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    private final TaskService taskService;

    //Constructor Injection
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    @GetMapping
    public List<Task> getAllTasks() {

        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task updatedTask){
      return taskService.updateTask(id,updatedTask)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        return taskService.deleteTask(id)?
                ResponseEntity.ok().build():
                ResponseEntity.notFound().build();
    }
    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status) {
        return taskService.getTasksByCompletionStatus(status);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTaskByTitle(title);
    }



}
