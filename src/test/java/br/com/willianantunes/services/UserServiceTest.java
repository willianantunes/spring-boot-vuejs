package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.services.dtos.UserDTO;
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

        UserDTO userDTO = UserDTO.builder()
            .name("Jafar")
            .email("jafar@agrabah.com")
            .password("my-lovely-jafar-master").build();

        User user = userService.createUser(userDTO);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getPassword().length()).isEqualTo(60);
        verify(passwordEncoder).encode(eq(userDTO.getPassword()));
        verify(userMapper).userDTOToUser(eq(userDTO));
    }

    @Test
    @DisplayName("Should return user by its e-mail")
    public void b() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        Optional<User> optionalJafar = userService.getUserByEmail("jafar@agrabah.com");

        assertThat(optionalJafar).isNotEmpty();
    }

    @Test
    @DisplayName("Should return all the users by its e-mail")
    public void c() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(scenarioBuilder.getMockedUsers().size());
    }

    @Test
    @DisplayName("Should delete the user by its e-mail")
    public void d() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();

        userService.deleteUserByEmail("jafar@agrabah.com");
        List<User> users = userService.getAllUsers();

        assertThat(users).hasSize(scenarioBuilder.getMockedUsers().size() - 1);
    }
}