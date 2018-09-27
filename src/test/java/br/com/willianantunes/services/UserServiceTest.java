package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.repositories.UserRepository;
import br.com.willianantunes.services.dtos.UserCreateDTO;
import br.com.willianantunes.services.dtos.UserDTO;
import br.com.willianantunes.services.dtos.UserUpdateDTO;
import br.com.willianantunes.services.mappers.UserMapper;
import br.com.willianantunes.support.ScenarioBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @SpyBean
    private PasswordEncoder passwordEncoder;
    @SpyBean
    private UserMapper userMapper;

    @Autowired
    private ScenarioBuilder scenarioBuilder;

    @BeforeEach
    void init() {

        scenarioBuilder.clearAllRepositories();
    }

    @Test
    @DisplayName("Should create user when DTO is valid")
    public void a() {

        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
            .name("Jafar")
            .email("jafar@agrabah.com")
            .password("my-lovely-jafar-master").build();

        UserDTO user = userService.createUser(userCreateDTO);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo(userCreateDTO.getName());
        assertThat(user.getEmail()).isEqualTo(userCreateDTO.getEmail());
        verify(passwordEncoder).encode(eq(userCreateDTO.getPassword()));
        verify(userMapper).userCreateDTOToUser(eq(userCreateDTO));
    }

    @Test
    @DisplayName("Should return user by its e-mail")
    public void b() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        Optional<UserDTO> optionalJafar = userService.getUserByEmail("jafar@agrabah.com");

        assertThat(optionalJafar).isNotEmpty();
    }

    @Test
    @DisplayName("Should return all the users by its e-mail")
    public void c() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        List<UserDTO> users = userService.getAllUsers();

        assertThat(users).hasSize(scenarioBuilder.getMockedUsers().size());
    }

    @Test
    @DisplayName("Should delete the user by its e-mail")
    public void d() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        userService.deleteUserByEmail("jafar@agrabah.com");
        List<UserDTO> users = userService.getAllUsers();

        assertThat(users).hasSize(scenarioBuilder.getMockedUsers().size() - 1);
    }

    @Test
    @DisplayName("Should update the user with new name and e-mail")
    public void e() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        List<User> currentUsers = userRepository.findAll();
        User anyUser = currentUsers.stream().findAny().orElseThrow();

        UserUpdateDTO userUpdateDTO = UserUpdateDTO.builder()
            .id(anyUser.getId().toString())
            .name("MY NAME IS SALT SHAKER")
            .email("salt-shaker@salt.com")
            .build();

        UserDTO userDTO = userService.updateUser(userUpdateDTO);

        assertThat(userDTO.getName()).isEqualTo(userUpdateDTO.getName());
        assertThat(userDTO.getEmail()).isEqualTo(userUpdateDTO.getEmail());
        assertThat(currentUsers.size()).isEqualTo(userRepository.count());
    }
}