package main.fseRESTApi.api.model.repositories;

import org.springframework.data.repository.CrudRepository;

import main.fseRESTApi.api.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
