package br.com.willianantunes.repositories;

import br.com.willianantunes.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findOneByNameIgnoreCase(String name);
    Optional<User> findOneByEmail(String email);
}