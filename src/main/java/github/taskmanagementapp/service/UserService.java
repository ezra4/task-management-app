package github.taskmanagementapp.service;

import github.taskmanagementapp.dto.CredentialsDTO;
import github.taskmanagementapp.dto.UserDTO;
import github.taskmanagementapp.dto.builder.UserBuilder;
import github.taskmanagementapp.model.User;
import github.taskmanagementapp.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers()
    {
        return userRepository.findAll()
                .stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }
    public UserDTO getUserByCredentials(CredentialsDTO credentialsDTO) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(credentialsDTO.getUsername(), credentialsDTO.getPassword());
        if (userOptional.isEmpty()) {
            LOGGER.error("Cannot find any user with the offered credentials!");
            return null;
        }
        else {
            User user = userOptional.get();
            return UserBuilder.toUserDTO(user);
        }
    }

    public UserDTO getUserByID(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()) {
            LOGGER.error("Cannot find user with the offered ID!");
            return null;
        }
        else {
            User user = userOptional.get();
            return UserBuilder.toUserDTO(user);
        }
    }

    public UserDTO registerUser(UserDTO userDTO)
    {
        User user = UserBuilder.toUser(userDTO);
        User savedUser = userRepository.save(user);
        return UserBuilder.toUserDTO(savedUser);
    }

    public void insertUser(UserDTO userDTO) {
        User user = UserBuilder.toUser(userDTO);
        userRepository.save(user);
    }
}
