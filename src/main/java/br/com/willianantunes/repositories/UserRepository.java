package br.com.willianantunes.repositories;

import br.com.willianantunes.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findOneByEmail(String email);

    @Query(value = "{ 'products.id' : ?0 }", fields = "{ 'id' : 1 }")
    Optional<User> findUserByProductId(String id);
}