package lev.ostrov.taskmanagement.service;

import jakarta.transaction.Transactional;
import lev.ostrov.taskmanagement.dto.TaskDTO;
import lev.ostrov.taskmanagement.model.Task;
import lev.ostrov.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::buildTaskDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(this::buildTaskDTO);
    }

    @Transactional
    public void addTask(TaskDTO task) {
        Task newTask = new Task(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted()
        );
        taskRepository.save(newTask);
    }

    @Transactional
    public void updateTask(Long id, TaskDTO updateTaskDTO) {
        taskRepository.findById(id).map(task -> {
            Task updatedTask = new Task(
                    id,
                    updateTaskDTO.getTitle(),
                    updateTaskDTO.getDescription(),
                    updateTaskDTO.getDueDate(),
                    updateTaskDTO.isCompleted()
            );
            return taskRepository.save(updatedTask);
        });
    }

    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskDTO buildTaskDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.isCompleted())
                .build();
    }
}
