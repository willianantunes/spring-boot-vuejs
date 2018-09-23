package br.com.willianantunes.repositories;

import br.com.willianantunes.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findOneByNameIgnoreCase(String name);
    Optional<User> findOneByEmail(String email);
}