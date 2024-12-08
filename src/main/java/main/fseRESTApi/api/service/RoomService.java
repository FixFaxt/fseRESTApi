package main.fseRESTApi.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.model.RoomUpdateDto;
import main.fseRESTApi.api.model.repositories.RoomRepository;

@Service
public class RoomService {
  @Autowired
  private RoomRepository roomRepository;

  public Room createRoom(Room room) {
    if (this.roomRepository.existsByName(room.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room with the same name already exists");
    }
    return this.roomRepository.save(room);
  }

  public List<Room> getAllRooms() {
    return this.roomRepository.findAll();
  }

  public Room getRoom(String name, UUID id) {
    Optional<Room> room;

    if (name != null && id != null) {
      // Prioritize search by id
      room = this.roomRepository.findById(id);
    } else if (name != null) {
      // Search by name only
      room = this.roomRepository.findByName(name);
    } else {
      // Search by id only
      room = this.roomRepository.findById(id);
    }

    return room.orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found with the given parameters"));
  }

  public Room updateRoom(UUID roomId, Room newRoom) {
    Room room = this.roomRepository.findById(roomId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with ID " + roomId + " not found"));

    room.setName(newRoom.getName());
    room.setCapacity(newRoom.getCapacity());

    return this.roomRepository.save(room);

  }

  public Room updateRoomPartial(UUID roomId, RoomUpdateDto roomUpdateDto) {
    Room room = this.roomRepository.findById(roomId).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with ID " + roomId + " not found"));

    if (roomUpdateDto.getName() != null) {
      room.setName(roomUpdateDto.getName());
    }
    if (roomUpdateDto.getCapacity() != null) {
      room.setCapacity(roomUpdateDto.getCapacity());
    }

    return this.roomRepository.save(room);

  }

  public void deleteRoom(UUID id) {
    this.roomRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room with ID " + id + " not found"));

    try {
      this.roomRepository.deleteById(id);
    } catch (DataAccessException ex) {
      // Handle db related errors
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR,
          "Database error occurred while deleting the room");
    }
  }
}
