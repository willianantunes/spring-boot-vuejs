package br.com.willianantunes.repositories;

import br.com.willianantunes.domain.Product;
import br.com.willianantunes.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Product> findByCode(@NotBlank String code) {

        Query query = new Query(where("products.code").is(code));

        return getProduct(query);
    }

    public Optional<Product> findById(@NotBlank String id) {

        Query query = new Query(where("products.id").is(id));

        return getProduct(query);

    }

    private Optional<Product> getProduct(Query query) {

        return mongoTemplate.find(query, User.class).stream()
            .map(User::getProducts)
            .filter(Predicate.not(List::isEmpty))
            .flatMap(Collection::stream)
            .findFirst();
    }
}