package com.javaproject.taskmanager.service;

import com.javaproject.taskmanager.entity.Task;
import com.javaproject.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService    {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);

    }
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setDescription(updatedTask.getDescription());
                    task.setTitle(updatedTask.getTitle());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }
    public boolean deleteTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task ->{
                    taskRepository.delete(task);
                    return true;
                } ).orElse(false);
    }
    public List<Task> getCompletedTasks(boolean status) {
        return taskRepository.findByCompleted(status);
    }
    public List<Task> searchTaskByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
}
