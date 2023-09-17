package github.taskmanagementapp.service;

import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.model.UserEntity;
import github.taskmanagementapp.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void registerUser_ReturnUser()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();

        UserDTO userDTO = UserDTO.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();

        when(userRepository.save(Mockito.any(UserEntity.class))).thenReturn(userEntity);
        UserDTO savedUser = userService.registerUser(userDTO);

        Assertions.assertThat(savedUser).isNotNull();
    }
}
