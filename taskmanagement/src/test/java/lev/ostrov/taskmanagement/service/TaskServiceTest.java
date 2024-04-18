package lev.ostrov.taskmanagement.service;

import lev.ostrov.taskmanagement.dto.TaskDTO;
import lev.ostrov.taskmanagement.model.Task;
import lev.ostrov.taskmanagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L, "Task 1", "Description 1", null, false));
        tasks.add(new Task(2L, "Task 2", "Description 2", null, false));

        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskDTO> taskDTOs = taskService.getAllTasks();

        assertEquals(2, taskDTOs.size());
        assertEquals("Task 1", taskDTOs.get(0).getTitle());
        assertEquals("Description 2", taskDTOs.get(1).getDescription());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task(1L, "Task 1", "Description 1", null, false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<TaskDTO> taskDTO = taskService.getTaskById(1L);

        assertTrue(taskDTO.isPresent());
        assertEquals("Task 1", taskDTO.get().getTitle());
    }

    @Test
    void testAddTask() {
        TaskDTO taskDTO = new TaskDTO(1L, "Task 1", "Description 1", null, false);
        taskService.addTask(taskDTO);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask() {
        TaskDTO updateTaskDTO = new TaskDTO(1L, "Updated Task", "Updated Description", null, true);
        Task task = new Task(1L, "Original Task", "Original Description", null, false); // Создаем фиктивный объект Task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task)); // Заглушка возвращает Optional с объектом Task

        taskService.updateTask(1L, updateTaskDTO);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testDeleteTaskById() {
        taskService.deleteTaskById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
