package main.fseRESTApi.api.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import main.fseRESTApi.api.model.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
  Optional<Room> findByName(String name);

  boolean existsByName(String name);
}