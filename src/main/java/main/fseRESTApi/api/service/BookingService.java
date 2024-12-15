package main.fseRESTApi.api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Booking;
import main.fseRESTApi.api.model.BookingUpdateDto;
import main.fseRESTApi.api.model.Room;
import main.fseRESTApi.api.model.repositories.BookingRepository;
import main.fseRESTApi.api.model.repositories.RoomRepository;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  @Autowired
  private RoomRepository roomRepository;

  public Booking createBooking(Booking booking) {
    boolean hasOverlap = this.bookingRepository.existsOverlappingBooking(
        booking.getRoom().getName(),
        booking.getStartTime(),
        booking.getEndTime(),
        null);

    if (hasOverlap) {
      throw new IllegalArgumentException("Booking times overlap with an existing booking.");
    }

    Room room = roomRepository.findByName(booking.getRoom().getName())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

    if (booking.getStartTime().isAfter(booking.getEndTime())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time must be before end time");
    }

    booking.setRoom(room);

    // Save the booking
    Booking savedBooking = bookingRepository.save(booking);

    // Update the room's booking list and save it
    room.getBookings().add(savedBooking);
    roomRepository.save(room);

    return savedBooking;
  }

  public List<Booking> getAllBookings() {
    return this.bookingRepository.findAll();
  }

  public Booking getBookingById(UUID id) {
    Booking booking = this.bookingRepository.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking with the ID " + id + " was not found"));

    return booking;
  }

  public List<Booking> getBookingsByRoomName(String roomName) {
    return this.bookingRepository.findByRoomName(roomName);
  }

  public Booking updateBooking(UUID id, Booking newBooking) {
    Booking booking = this.bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking with ID" + id + "not found"));

    booking.setEmail(newBooking.getEmail());
    booking.setStartTime(newBooking.getStartTime());
    booking.setEndTime(newBooking.getEndTime());

    if (booking.getStartTime() != null && booking.getEndTime() != null) {
      if (booking.getStartTime().isAfter(booking.getEndTime())
          || booking.getStartTime().isEqual(booking.getEndTime())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Start time must be before end time.");
      }
    }

    Room room = this.roomRepository.findByName(newBooking.getRoomName())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "New room was not found"));

    booking.setRoom(room);

    boolean hasOverlap = this.bookingRepository.existsOverlappingBooking(
        newBooking.getRoomName(),
        newBooking.getStartTime(),
        newBooking.getEndTime(),
        booking.getId());

    if (hasOverlap) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Booking times overlap with an existing booking.");
    }

    return this.bookingRepository.save(booking);
  }

  public Booking updateBookingPartial(UUID id, BookingUpdateDto bookingUpdateDto) {
    Booking booking = this.bookingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking with ID" + id + "was not found"));

    if (bookingUpdateDto.getStartTime() != null) {
      booking.setStartTime(bookingUpdateDto.getStartTime());
    }
    if (bookingUpdateDto.getEndTime() != null) {
      booking.setEndTime(bookingUpdateDto.getEndTime());
    }
    if (bookingUpdateDto.getEmail() != null) {
      booking.setEmail(bookingUpdateDto.getEmail());
    }
    if (bookingUpdateDto.getRoom() != null) {
      Room room = this.roomRepository.findByName(bookingUpdateDto.getRoomName())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
              "Room with name " + bookingUpdateDto.getRoomName() + "was not found"));

      booking.setRoom(room);
    }

    if (booking.getStartTime() != null && booking.getEndTime() != null) {
      if (booking.getStartTime().isAfter(booking.getEndTime())
          || booking.getStartTime().isEqual(booking.getEndTime())) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Start time must be before end time.");
      }
    }

    boolean hasOverlap = this.bookingRepository.existsOverlappingBooking(
        booking.getRoomName(),
        booking.getStartTime(),
        booking.getEndTime(),
        booking.getId());

    if (hasOverlap) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Booking times overlap with an existing booking.");
    }

    return this.bookingRepository.save(booking);
  }

  public void deleteBooking(UUID id) {
    this.bookingRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking with ID " + id + "was not found"));

    try {
      this.bookingRepository.deleteById(id);
    } catch (DataAccessException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Database error occurred while deleting the booking");
    }
  }
}