package romanmufid.springmongo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import romanmufid.springmongo.dto.CreateTaskDto;
import romanmufid.springmongo.dto.TaskResponse;
import romanmufid.springmongo.dto.WebResponse;
import romanmufid.springmongo.entity.Task;
import romanmufid.springmongo.repository.TaskRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void createTaskBadRequest() throws Exception {
        CreateTaskDto request = new CreateTaskDto();
        request.setTitle("");
        request.setDescription("");

        mockMvc.perform(
                post("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<TaskResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });
            assertFalse(response.isSuccess());
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void createTaskSuccess() throws Exception {
        CreateTaskDto request = new CreateTaskDto();
        request.setTitle("Test");
        request.setDescription("Test Description");

        mockMvc.perform(
                post("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TaskResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });
            assertNull(response.getErrors());
            assertTrue(response.isSuccess());
            assertEquals("Test", response.getData().getTitle());
            assertEquals("Test Description", response.getData().getDescription());

            assertTrue(taskRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void listSuccess() throws Exception {
        for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setTitle("Test" + i);
            task.setDescription("Test Description" + i);
            taskRepository.save(task);
        }

        mockMvc.perform(
                get("/api/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<TaskResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });
            assertNull(response.getErrors());
            assertTrue(response.isSuccess());
            assertEquals(5, response.getData().size());
        });
    }

    @Test
    void getTaskByTitleNotFound() throws Exception {
        mockMvc.perform(
                get("/api/tasks/wrong")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });
            assertFalse(response.isSuccess());
            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getTaskByTitleSuccess() throws Exception {
        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("Test Description");

        taskRepository.save(task);

        mockMvc.perform(
                get("/api/tasks/Test")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<TaskResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                    new TypeReference<>() {
                    });
            assertTrue(response.isSuccess());
            assertNull(response.getErrors());

            assertEquals("Test", response.getData().getTitle());
            assertEquals("Test Description", response.getData().getDescription());
        });
    }
}