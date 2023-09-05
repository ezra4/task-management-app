package github.taskmanagementapp.repository;

import github.taskmanagementapp.model.User;
import github.taskmanagementapp.repo.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTests(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Test
    public void GetAllUsers_ReturnMoreThan1()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        User user2 = User.builder()
                .username("David")
                .password("parola")
                .email("david@yahoo.com")
                .build();
        userRepository.save(user);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        Assertions.assertThat(users).isNotNull();
    }

    @Test
    public void GetByID_ReturnUser()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).get();

        Assertions.assertThat(savedUser).isNotNull();
    }

    @Test
    public void GetByCredentials_ReturnUser()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(user);

        User loggedInUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).get();

        Assertions.assertThat(loggedInUser).isNotNull();
    }

    @Test
    public void SaveUser_ReturnSavedUser()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isExactlyInstanceOf(UUID.class);
    }

    @Test
    public void UpdateUser_ReturnNewUser()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(user);

        User savedUser = userRepository.findById(user.getId()).get();
        savedUser.setUsername("David");
        savedUser.setPassword("password");
        savedUser.setEmail("david@gmail.com");
        userRepository.save(savedUser);
        User updatedUser = userRepository.findById(savedUser.getId()).get();


        Assertions.assertThat(updatedUser.getUsername()).isEqualTo("David");
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("password");
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("david@gmail.com");
    }

    @Test
    public void DeleteUser_ReturnUserIsEmpty()
    {
        User user = User.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(user);

        userRepository.deleteById(user.getId());
        Optional<User> deletedUser = userRepository.findById(user.getId());

        Assertions.assertThat(deletedUser).isEmpty();
    }
}
