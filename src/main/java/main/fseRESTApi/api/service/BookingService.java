package main.fseRESTApi.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.fseRESTApi.api.model.Booking;
import main.fseRESTApi.api.model.repositories.BookingRepository;

@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

  public List<Booking> getBookingsByEmail(String email) {
    return bookingRepository.findByEmail(email);
  }
}