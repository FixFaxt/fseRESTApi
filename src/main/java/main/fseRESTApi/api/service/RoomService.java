package main.fseRESTApi.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Room;
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
}
