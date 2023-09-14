package github.taskmanagementapp.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDTO {
    UUID id;
    String username;
    String password;
    String email;
}
