package main.fseRESTApi.api.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

  public Optional<Room> getRoomByName(String roomName) {
    return this.roomRepository.findByName(roomName);
  }

  public Optional<Room> getRoomById(UUID roomId) {
    return this.roomRepository.findById(roomId);
  }

  public Room updateRoom(UUID roomId, Room newRoom) {
    Optional<Room> existingRoom = this.getRoomById(roomId);

    if (existingRoom.isPresent()) {
      Room room = existingRoom.get();

      room.setName(newRoom.getName());
      room.setCapacity(newRoom.getCapacity());

      return this.roomRepository.save(room);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found");
    }
  }

  public Room updateRoomPartial(UUID roomId, RoomUpdateDto roomUpdateDto) {
    Optional<Room> existingRoom = this.getRoomById(roomId);

    if (existingRoom.isPresent()) {
      Room room = existingRoom.get();

      if (roomUpdateDto.getName() != null) {
        room.setName(roomUpdateDto.getName());
      }

      if (roomUpdateDto.getCapacity() != null) {
        room.setCapacity(roomUpdateDto.getCapacity());
      }

      return this.roomRepository.save(room);
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found");
    }
  }
}
