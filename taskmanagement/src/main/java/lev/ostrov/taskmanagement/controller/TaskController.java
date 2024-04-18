package lev.ostrov.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import lev.ostrov.taskmanagement.dto.TaskDTO;
import lev.ostrov.taskmanagement.model.Task;
import lev.ostrov.taskmanagement.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks")
    public List<TaskDTO> getAllTasks(){
       return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public Optional<TaskDTO> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PostMapping
    @Operation(summary = "Add new task")
    public void addNewTask(@RequestBody TaskDTO task){
        taskService.addTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task by id")
    public void updateTaskById(@PathVariable Long id, @RequestBody TaskDTO task){
        taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by id")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }
}
