package romanmufid.springmongo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import romanmufid.springmongo.dto.CreateTaskDto;
import romanmufid.springmongo.dto.TaskResponse;
import romanmufid.springmongo.entity.Task;
import romanmufid.springmongo.entity.User;
import romanmufid.springmongo.repository.TaskRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ValidationService validationService;

    private TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId().toString())
                .userId(task.getUserId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public TaskResponse create(User user, CreateTaskDto request) {
        validationService.validate(request);
        Task task = new Task();
        task.setUserId(user.getId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : "pending");
        task.setCreatedAt(new Date());

        taskRepository.save(task);

        return toTaskResponse(task);
    }

    public List<TaskResponse> list(User user) {
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return tasks.stream().map(this::toTaskResponse).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskByTitle(String title) {
        Task task = taskRepository.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        return toTaskResponse(task);
    }
}
