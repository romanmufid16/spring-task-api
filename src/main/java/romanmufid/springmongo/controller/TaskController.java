package romanmufid.springmongo.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import romanmufid.springmongo.dto.CreateTaskDto;
import romanmufid.springmongo.dto.TaskResponse;
import romanmufid.springmongo.dto.WebResponse;
import romanmufid.springmongo.entity.User;
import romanmufid.springmongo.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "Bearer Authentication")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TaskResponse> create(@RequestBody CreateTaskDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        TaskResponse taskResponse = taskService.create(user, request);
        return WebResponse.<TaskResponse>builder()
                .success(true)
                .message("Task created successfully")
                .data(taskResponse)
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<TaskResponse>> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<TaskResponse> taskResponseList = taskService.list(user);
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
