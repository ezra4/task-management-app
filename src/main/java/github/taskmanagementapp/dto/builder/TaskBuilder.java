package github.taskmanagementapp.dto.builder;

import github.taskmanagementapp.dto.TaskDTO;
import github.taskmanagementapp.model.Task;

public class TaskBuilder {
    public static TaskDTO toTaskDTO(Task task)
    {
        return new TaskDTO(task.getId(),task.getTitle(), task.getDescription(), task.getDueDate(), task.getUserEntity());
    }

    public static Task toTask(TaskDTO taskDTO)
    {
        return new Task(taskDTO.getId(), taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate(), taskDTO.getUserEntity());
    }
}
