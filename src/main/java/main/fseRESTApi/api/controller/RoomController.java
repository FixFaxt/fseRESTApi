package main.fseRESTApi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.model.RoomUpdateDto;
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

  @PutMapping("/{id}")
  public ResponseEntity<Room> updateRoom(@PathVariable("id") UUID id, @RequestBody Room room) {
    Room updatedRoom = this.roomService.updateRoom(id, room);
    return ResponseEntity.ok(updatedRoom);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Room> updateRoomPartial(
      @PathVariable("id") UUID id,
      @RequestBody RoomUpdateDto roomUpdateDto) {

    Room updatedRoom = roomService.updateRoomPartial(id, roomUpdateDto);
    return ResponseEntity.ok(updatedRoom);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRoom(@PathVariable("id") UUID id) {
    this.roomService.deleteRoom(id);
    return ResponseEntity.noContent().build();
  }
}
