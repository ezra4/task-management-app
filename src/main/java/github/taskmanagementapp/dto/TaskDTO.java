package github.taskmanagementapp.dto;

import github.taskmanagementapp.model.User;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskDTO {
    UUID id;
    String title;
    String description;
    Date dueDate;
    User user;
}
