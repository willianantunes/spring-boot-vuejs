package br.com.willianantunes.services;

import br.com.willianantunes.domain.User;
import br.com.willianantunes.services.dtos.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void shouldCreateUserWhenDTOIsValid() {

        UserDTO userDTO = UserDTO.builder()
            .name("Jafar")
            .email("jafar@agrabah.com")
            .password("my-lovely-iago-parrot").build();

        User user = userService.createUser(userDTO);

        assertThat(user.getId()).isNotBlank();
        assertThat(user.getPassword().length()).isEqualTo(60);
    }
}