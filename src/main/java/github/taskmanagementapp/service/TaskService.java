package github.taskmanagementapp.service;

import github.taskmanagementapp.dto.TaskDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.dto.builder.TaskBuilder;
import github.taskmanagementapp.model.Task;
import github.taskmanagementapp.model.User;
import github.taskmanagementapp.repo.TaskRepository;
import github.taskmanagementapp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskDTO> getAllTasksOfAUser(UserDTO userDTO)
    {
        List<TaskDTO> result = new ArrayList<>();
        Optional<User> userOptional = userRepository.findById(userDTO.getId());
        if(userOptional.isEmpty())
        {
            LOGGER.error("User doesn't exist!");
        }
        else
        {
            User user = userOptional.get();
            List<Task> taskList = taskRepository.findAllByUser(user);
            result = taskList.stream().map(TaskBuilder::toTaskDTO).collect(Collectors.toList());
        }
        return result;
    }

    public List<TaskDTO> getAllTasks()
    {
        return taskRepository.findAll().stream().map(TaskBuilder::toTaskDTO).collect(Collectors.toList());
    }
}
