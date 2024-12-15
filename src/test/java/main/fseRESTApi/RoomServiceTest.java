package main.fseRESTApi;

import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.model.repositories.RoomRepository;
import main.fseRESTApi.api.service.RoomService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoomServiceTest {

  @InjectMocks
  private RoomService roomService;

  @Mock
  private RoomRepository roomRepository;

  RoomServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRoom_success() {

    Room room = new Room();
    room.setName("Room1");

    when(roomRepository.existsByName("Room1")).thenReturn(false);
    when(roomRepository.save(room)).thenReturn(room);

    Room savedRoom = roomService.createRoom(room);

    assertNotNull(savedRoom);
    assertEquals("Room1", savedRoom.getName());
    verify(roomRepository).save(room);
  }

  @Test
  void createRoom_duplicateName() {

    Room room = new Room();
    room.setName("Room1");

    when(roomRepository.existsByName("Room1")).thenReturn(true);

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      roomService.createRoom(room);
    });
    assertEquals("400 BAD_REQUEST \"Room with the same name already exists\"", exception.getMessage());
  }

  @Test
  void getRoom_notFound() {

    when(roomRepository.findById(any())).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      roomService.getRoom(null, UUID.randomUUID());
    });
    assertEquals("404 NOT_FOUND \"Room not found with the given parameters\"", exception.getMessage());
  }

  @Test
  void updateRoom_notFound() {

    UUID roomId = UUID.randomUUID();
    when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      roomService.updateRoom(roomId, new Room());
    });
    assertEquals("404 NOT_FOUND \"Room with ID " + roomId + " not found\"", exception.getMessage());
  }

  @Test
  void deleteRoom_success() {

    UUID roomId = UUID.randomUUID();
    Room room = new Room();
    when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));

    roomService.deleteRoom(roomId);

    verify(roomRepository).deleteById(roomId);
  }

  @Test
  void deleteRoom_notFound() {

    UUID roomId = UUID.randomUUID();
    when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      roomService.deleteRoom(roomId);
    });
    assertEquals("404 NOT_FOUND \"Room with ID " + roomId + " not found\"", exception.getMessage());
  }
}
