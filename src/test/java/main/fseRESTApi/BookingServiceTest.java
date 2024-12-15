package main.fseRESTApi;

import main.fseRESTApi.api.model.Booking;
import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.model.repositories.BookingRepository;
import main.fseRESTApi.api.model.repositories.RoomRepository;
import main.fseRESTApi.api.service.BookingService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingServiceTest {

  @InjectMocks
  private BookingService bookingService;

  @Mock
  private BookingRepository bookingRepository;

  @Mock
  private RoomRepository roomRepository;

  BookingServiceTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createBooking_success() {

    Room room = new Room();
    room.setName("Room1");

    Booking booking = new Booking();
    booking.setRoom(room);
    booking.setStartTime(LocalDateTime.now().plusHours(1));
    booking.setEndTime(LocalDateTime.now().plusHours(2));

    when(roomRepository.findByName("Room1")).thenReturn(Optional.of(room));
    when(bookingRepository.existsOverlappingBooking("Room1", booking.getStartTime(), booking.getEndTime(), null))
        .thenReturn(false);
    when(bookingRepository.save(booking)).thenReturn(booking);

    Booking savedBooking = bookingService.createBooking(booking);

    assertNotNull(savedBooking);
    assertEquals(booking, savedBooking);
    verify(bookingRepository).save(booking);
  }

  @Test
  void createBooking_overlap() {

    Booking booking = new Booking();
    Room room = new Room();
    room.setName("Room1");
    booking.setRoom(room);
    booking.setStartTime(LocalDateTime.now().plusHours(1));
    booking.setEndTime(LocalDateTime.now().plusHours(2));

    when(bookingRepository.existsOverlappingBooking("Room1", booking.getStartTime(), booking.getEndTime(), null))
        .thenReturn(true);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      bookingService.createBooking(booking);
    });
    assertEquals("Booking times overlap with an existing booking.", exception.getMessage());
  }

  @Test
  void createBooking_roomNotFound() {

    Booking booking = new Booking();
    Room room = new Room();
    room.setName("NonExistentRoom");
    booking.setRoom(room);

    when(roomRepository.findByName("NonExistentRoom")).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      bookingService.createBooking(booking);
    });
    assertEquals("404 NOT_FOUND \"Room not found\"", exception.getMessage());
  }

  @Test
  void getBookingById_notFound() {

    UUID id = UUID.randomUUID();
    when(bookingRepository.findById(id)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      bookingService.getBookingById(id);
    });
    assertEquals("404 NOT_FOUND \"Booking with the ID " + id + " was not found\"", exception.getMessage());
  }

  @Test
  void deleteBooking_notFound() {

    UUID id = UUID.randomUUID();
    when(bookingRepository.findById(id)).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      bookingService.deleteBooking(id);
    });
    assertEquals("404 NOT_FOUND \"Booking with ID " + id + "was not found\"", exception.getMessage());
  }
}
