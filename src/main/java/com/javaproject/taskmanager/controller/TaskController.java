package com.javaproject.taskmanager.controller;

import com.javaproject.taskmanager.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/tasks")

public class TaskController {
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        task.setId(nextId++);
        tasks.add(task);
        return task;
    }
    @PutMapping
    public Task updateTask(@PathVariable Long id,@RequestBody Task updatedTask){
        for(int i=0;i<tasks.size();i++){
            Task task = tasks.get(i);
            if(task.getId().equals(id)){
                updatedTask.setId(id);
                updatedTask.setCreatedAt(task.getCreatedAt());
                tasks.set(i,updatedTask);
                return updatedTask;
            }
        }
        return null;
    }

    @DeleteMapping
    public void deleteTask(@PathVariable Long id){
        tasks.removeIf(task -> task.getId().equals(id));
    }
}
