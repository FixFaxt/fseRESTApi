package main.fseRESTApi.api.model.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import main.fseRESTApi.api.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE u.id = :id")
  Optional<User> findUserById(@Param("id") UUID id);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(@Param("email") String email);
}
