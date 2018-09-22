package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.services.dtos.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Should create user when DTO is valid")
    public void a() {

        UserDTO userDTO = UserDTO.builder()
            .name("Jafar")
            .email("jafar@agrabah.com")
            .password("my-lovely-iago-parrot").build();

        User user = userService.createUser(userDTO);

        assertThat(user.getId()).isNotBlank();
        assertThat(user.getPassword().length()).isEqualTo(60);
    }
}