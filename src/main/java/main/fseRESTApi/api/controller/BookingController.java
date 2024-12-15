package main.fseRESTApi.api.controller;

import java.util.List;
import java.util.UUID;

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

  @GetMapping("/{id}")
  public Booking getBookingById(@PathVariable("id") UUID id) {
    Booking booking = this.bookingService.getBookingById(id);
    System.out.println(booking.toString());
    return booking;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Booking> updateBooking(@PathVariable("id") UUID id, @RequestBody Booking booking) {
    Booking updatedBooking = this.bookingService.updateBooking(id, booking);
    return ResponseEntity.ok(updatedBooking);
  }
}
