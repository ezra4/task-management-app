package github.taskmanagementapp.dto.builder;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.model.UserEntity;

public class UserBuilder {

    public static CredentialsDTO toCredentialsDTO(UserEntity userEntity)
    {
        return new CredentialsDTO(userEntity.getUsername(), userEntity.getPassword());
    }

    public static UserDTO toUserDTO(UserEntity userEntity)
    {
        return new UserDTO(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail(), userEntity.getRole());
    }

    public static UserEntity toUser(UserDTO userDTO)
    {
        return new UserEntity(userDTO.getId(),userDTO.getUsername(),userDTO.getPassword(),userDTO.getEmail(), userDTO.getRole());
    }
}
