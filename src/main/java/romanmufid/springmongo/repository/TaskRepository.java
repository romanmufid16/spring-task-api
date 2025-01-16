package romanmufid.springmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import romanmufid.springmongo.entity.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findByTitle(String title);
}
