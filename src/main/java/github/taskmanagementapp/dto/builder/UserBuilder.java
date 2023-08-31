package github.taskmanagementapp.dto.builder;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.model.User;

public class UserBuilder {

    public static CredentialsDTO toCredentialsDTO(User user)
    {
        return new CredentialsDTO(user.getUsername(), user.getPassword());
    }

    public static UserDTO toUserDTO(User user)
    {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }

    public static User toUser(UserDTO userDTO)
    {
        return new User(userDTO.getId(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail());
    }
}
