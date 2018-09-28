package br.com.willianantunes.repositories;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.domain.User;
import br.com.willianantunes.support.ScenarioBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScenarioBuilder scenarioBuilder;

    @BeforeEach
    void init() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();
    }

    @AfterEach
    void tearDown() {

        scenarioBuilder.clearAllRepositories();
    }

    @Test
    @DisplayName("Should return an user only with his id")
    public void a() {

        User anyUser = userRepository.findAll().stream().findAny().orElseThrow();
        Product anyProduct = anyUser.getProducts().stream().findAny().orElseThrow();

        Optional<User> optionalUser = userRepository.findUserByProductId(anyProduct.getId());

        assertThat(optionalUser).isNotEmpty();
        assertThat(optionalUser.get())
            .extracting(User::getId, User::getPassword, User::getEmail, User::getName, User::getProducts)
            .containsExactly(anyUser.getId(), null, null, null, null);
    }
}