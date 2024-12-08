package main.fseRESTApi.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
  @Autowired
  private RoomService roomService;

  @PostMapping()
  public Room createNewRoom(@RequestBody Room room) {
    Room newRoom = this.roomService.createRoom(room);
    return newRoom;
  }

  @GetMapping()
  public List<Room> getAllRooms() {
    return this.roomService.getAllRooms();
  }

  @GetMapping("/{name}")
  public Room getRoomByName(@PathVariable("name") String roomName) {
    Optional<Room> room = this.roomService.getRoomByName(roomName);
    if (!room.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room was not found");
    }
    return room.get();
  }
}
