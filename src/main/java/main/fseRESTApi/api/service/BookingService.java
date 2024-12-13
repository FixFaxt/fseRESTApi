package main.fseRESTApi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import main.fseRESTApi.api.model.Booking;
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
    boolean hasOverlap = bookingRepository.existsOverlappingBooking(
        booking.getRoom().getId(),
        booking.getStartTime(),
        booking.getEndTime());

    if (hasOverlap) {
      throw new IllegalArgumentException("Booking times overlap with an existing booking.");
    }
    // Fix overlap detection

    System.out.println(hasOverlap);

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

  public List<Booking> getBookingsByEmail(String email) {
    return bookingRepository.findByEmail(email);
  }
}