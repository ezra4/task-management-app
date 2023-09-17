package github.taskmanagementapp.repository;

import github.taskmanagementapp.model.UserEntity;
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
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        UserEntity userEntity2 = UserEntity.builder()
                .username("David")
                .password("parola")
                .email("david@yahoo.com")
                .build();
        userRepository.save(userEntity);
        userRepository.save(userEntity2);

        List<UserEntity> userEntities = userRepository.findAll();

        Assertions.assertThat(userEntities).isNotNull();
    }

    @Test
    public void GetByID_ReturnUser()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(userEntity);

        UserEntity savedUserEntity = userRepository.findById(userEntity.getId()).get();

        Assertions.assertThat(savedUserEntity).isNotNull();
    }

    @Test
    public void GetByCredentials_ReturnUser()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(userEntity);

        UserEntity loggedInUserEntity = userRepository.findByUsernameAndPassword(userEntity.getUsername(), userEntity.getPassword()).get();

        Assertions.assertThat(loggedInUserEntity).isNotNull();
    }

    @Test
    public void SaveUser_ReturnSavedUser()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);

        Assertions.assertThat(savedUserEntity).isNotNull();
        Assertions.assertThat(savedUserEntity.getId()).isExactlyInstanceOf(UUID.class);
    }

    @Test
    public void UpdateUser_ReturnNewUser()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(userEntity);

        UserEntity savedUserEntity = userRepository.findById(userEntity.getId()).get();
        savedUserEntity.setUsername("David");
        savedUserEntity.setPassword("password");
        savedUserEntity.setEmail("david@gmail.com");
        userRepository.save(savedUserEntity);
        UserEntity updatedUserEntity = userRepository.findById(savedUserEntity.getId()).get();


        Assertions.assertThat(updatedUserEntity.getUsername()).isEqualTo("David");
        Assertions.assertThat(updatedUserEntity.getPassword()).isEqualTo("password");
        Assertions.assertThat(updatedUserEntity.getEmail()).isEqualTo("david@gmail.com");
    }

    @Test
    public void DeleteUser_ReturnUserIsEmpty()
    {
        UserEntity userEntity = UserEntity.builder()
                .username("Vlad")
                .password("parola")
                .email("vlad@yahoo.com")
                .build();
        userRepository.save(userEntity);

        userRepository.deleteById(userEntity.getId());
        Optional<UserEntity> deletedUser = userRepository.findById(userEntity.getId());

        Assertions.assertThat(deletedUser).isEmpty();
    }
}
