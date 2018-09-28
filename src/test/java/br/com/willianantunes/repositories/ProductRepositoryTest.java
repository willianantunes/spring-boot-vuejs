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
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ScenarioBuilder scenarioBuilder;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void init() throws IOException {

        scenarioBuilder.createMockedUsersOnDatabase();
    }

    @AfterEach
    void tearDown() {

        scenarioBuilder.clearAllRepositories();
    }

    @Test
    @DisplayName("Should find a product by its id")
    public void a() {

        validateQuery(product -> productRepository.findById(product.getId()));
    }

    @Test
    @DisplayName("Should find a product by its code")
    public void b() {

        validateQuery(product -> productRepository.findByCode(product.getCode()));
    }

    private void validateQuery(Function<Product, Optional<Product>> evaluateQuery) {

        User anyUser = userRepository.findAll().stream().findAny().orElseThrow();
        Product anyProduct = anyUser.getProducts().stream().findAny().orElseThrow();

        Optional<Product> optionalProduct = evaluateQuery.apply(anyProduct);

        assertThat(optionalProduct).isNotEmpty();
        assertThat(optionalProduct.get())
            .extracting(Product::getId, Product::getCode, Product::getName,
                Product::getPrice, Product::getDetails, Product::getImageLink)
            .containsExactly(anyProduct.getId(), anyProduct.getCode(), anyProduct.getName(),
                anyProduct.getPrice(), anyProduct.getDetails(), anyProduct.getImageLink());
    }
}