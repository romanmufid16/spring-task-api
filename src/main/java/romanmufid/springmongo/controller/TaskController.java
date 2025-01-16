package romanmufid.springmongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import romanmufid.springmongo.dto.CreateTaskDto;
import romanmufid.springmongo.dto.TaskResponse;
import romanmufid.springmongo.dto.WebResponse;
import romanmufid.springmongo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> create(@RequestBody CreateTaskDto request) {
        TaskResponse taskResponse = taskService.create(request);
        return WebResponse.<TaskResponse>builder()
                .success(true)
                .message("Task created successfully")
                .data(taskResponse)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<TaskResponse>> list() {
        List<TaskResponse> taskResponseList = taskService.list();
        return WebResponse.<List<TaskResponse>>builder()
                .success(true)
                .message("Task retrieved successfully")
                .data(taskResponseList)
                .build();
    }

    @GetMapping(path = "/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> get(@PathVariable String title) {
        TaskResponse response = taskService.getTaskByTitle(title);
        return WebResponse.<TaskResponse>builder()
                .success(true)
                .message("Task retrieved successfully")
                .data(response)
                .build();
    }
}
