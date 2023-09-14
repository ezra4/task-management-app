package github.taskmanagementapp.repo;

import github.taskmanagementapp.model.Task;
import github.taskmanagementapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByUser(User user);
}
