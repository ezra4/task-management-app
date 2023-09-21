package github.taskmanagementapp.service;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.dto.builder.UserBuilder;
import github.taskmanagementapp.model.Role;
import github.taskmanagementapp.model.UserEntity;
import github.taskmanagementapp.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll()
                .stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }
    public Boolean verifyPassword(String password, String encodedPassword)
    {
        return passwordEncoder.matches(password,encodedPassword);
    }
    public UserDTO getUserByCredentials(CredentialsDTO credentialsDTO) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(credentialsDTO.getUsername());
        if (userOptional.isEmpty()) {
            LOGGER.error("Cannot find any user with the offered credentials!");
            return null;
        }
        else {
            UserEntity userEntity = userOptional.get();
            String encodedPassword = userEntity.getPassword();
            boolean passwordMatches = verifyPassword(credentialsDTO.getPassword(),encodedPassword);
            if(passwordMatches)
                return UserBuilder.toUserDTO(userEntity);
            else {
                LOGGER.error("Password is incorrect!");
                return null;
            }
        }
    }

    public UserDTO getUserByID(UUID id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            LOGGER.error("Cannot find user with the offered ID!");
            return null;
        }
        else {
            UserEntity userEntity = userOptional.get();
            return UserBuilder.toUserDTO(userEntity);
        }
    }

    public UserDTO registerUser(UserDTO userDTO)
    {
        UserEntity user = UserBuilder.toUser(userDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUser = userRepository.save(user);
        return UserBuilder.toUserDTO(savedUser);
    }

    public Boolean userExistsByUsername(String username)
    {
        return userRepository.existsByUsername(username);
    }

}
