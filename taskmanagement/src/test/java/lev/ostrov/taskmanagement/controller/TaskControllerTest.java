package lev.ostrov.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lev.ostrov.taskmanagement.dto.TaskDTO;
import lev.ostrov.taskmanagement.model.Task;
import lev.ostrov.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllTasks() throws Exception {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        // Populate taskDTOList with test data
        when(taskService.getAllTasks()).thenReturn(taskDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetTaskById() throws Exception {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        // Populate taskDTO with test data
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(taskDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(taskId));
    }

    @Test
    void testAddNewTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        // Заполните taskDTO тестовыми данными, если это необходимо

        mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Проверяем, что метод addTask был вызван с соответствующим объектом TaskDTO
        verify(taskService, times(1)).addTask(any(TaskDTO.class));
    }


    @Test
    void testUpdateTaskById() throws Exception {
        Long taskId = 1L;
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(taskId);
        // Populate taskDTO with test data
        mockMvc.perform(MockMvcRequestBuilders.put("/tasks/{id}", taskId)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(taskService, times(1)).updateTask(eq(taskId), any(TaskDTO.class));
    }

    @Test
    void testDeleteTaskById() throws Exception {
        Long taskId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/{id}", taskId))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTaskById(taskId);
    }
}
