package romanmufid.springmongo.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import romanmufid.springmongo.entity.Task;
import romanmufid.springmongo.repository.TaskRepository;

import java.util.Date;
import java.util.List;

@Component
public class TaskSeeder implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        taskRepository.deleteAll();

        List<Task> tasks = List.of(
                new Task(null, "Task 1", "Description 1", "pending", new Date()),
                new Task(null, "Task 2", "Description 2", "completed", new Date()),
                new Task(null, "Task 3", "Description 3", "in-progress", new Date())
        );

        taskRepository.saveAll(tasks);

        System.out.println("Data seeder berhasil dimasukkan ke database.");
    }
}
