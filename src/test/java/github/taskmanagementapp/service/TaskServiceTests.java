package github.taskmanagementapp.service;

import github.taskmanagementapp.dto.TaskDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.model.Task;
import github.taskmanagementapp.model.UserEntity;
import github.taskmanagementapp.repo.TaskRepository;
import github.taskmanagementapp.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void GetAllTasksFromAUser_ReturnListOfTasks()
    {
        UUID id = UUID.randomUUID();
        UserDTO userDTO = UserDTO.builder()
                .id(id)
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        Task task = Task.builder()
                .userEntity(userEntity)
                .title("Curatenie")
                .description("Curatenie")
                .dueDate(new Date())
                .build();
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(userEntity));
        when(taskRepository.findAllByUserEntity(Mockito.any(UserEntity.class))).thenReturn(taskList);

        List<TaskDTO> savedList = taskService.getAllTasksOfAUser(userDTO);

        Assertions.assertThat(savedList).isNotNull();
    }

    @Test
    public void GetAllTasks_ReturnListOfTasks()
    {
        List<Task> taskList = Mockito.mock(List.class);

        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskDTO> savedList = taskService.getAllTasks();
        Assertions.assertThat(savedList).isNotNull();
    }
}
