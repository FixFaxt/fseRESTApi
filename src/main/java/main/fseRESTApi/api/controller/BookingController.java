package main.fseRESTApi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import main.fseRESTApi.api.model.Booking;
import main.fseRESTApi.api.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
  @Autowired
  private BookingService bookingService;

  @PostMapping()
  public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
    try {
      Booking newBooking = bookingService.createBooking(booking);
      return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
  }

  @GetMapping()
  public List<Booking> getAllBookings() {
    return this.bookingService.getAllBookings();
  }
}
