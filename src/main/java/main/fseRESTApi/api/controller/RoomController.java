package main.fseRESTApi.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

  @GetMapping("/room")
  public Room getRoomByName(@RequestParam(required = false) String name, @RequestParam(required = false) UUID id) {
    if (name == null && id == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Either 'name' or 'id' query parameter must be provided");
    }

    return roomService.getRoom(name, id);
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
