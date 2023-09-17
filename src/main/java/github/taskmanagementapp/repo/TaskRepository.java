package github.taskmanagementapp.repo;

import github.taskmanagementapp.model.Task;
import github.taskmanagementapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByUserEntity(UserEntity userEntity);
}
