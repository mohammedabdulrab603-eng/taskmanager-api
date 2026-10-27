package com.javaproject.taskmanager.controller;

import com.javaproject.taskmanager.entity.Task;
import com.javaproject.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    private final TaskRepository taskRepository;

    //Constructor Injection
    @Autowired
    public TaskController(TaskRepository taskrepository){
        this.taskRepository = taskrepository;
    }



    @GetMapping
    public List<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
    @PutMapping
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@RequestBody Task updatedTask){
      return taskRepository.findById(id)
              .map(task ->  {
                  task.setDescription(updatedTask.getDescription());
                  task.setTitle(updatedTask.getTitle());
                  task.setCompleted(updatedTask.getCompleted());
                  Task savedTask = taskRepository.save(task);
                  return ResponseEntity.ok(savedTask);
              }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        return taskRepository.findById(id)
                .map(task ->{
                    taskRepository.delete(task);
                    return ResponseEntity.ok().<Void>build();
                } ).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status) {
        return taskRepository.findByCompleted(status);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }



}
