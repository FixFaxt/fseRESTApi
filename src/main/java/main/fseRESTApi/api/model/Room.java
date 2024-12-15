package main.fseRESTApi.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rooms")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  @NotBlank
  private String name;

  @Column(nullable = false)
  @NotNull
  private Integer capacity;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Booking> bookings = new ArrayList<>();;

  public Room() {
  }

  public Room(String name, Integer capacity) {
    this.name = name;
    this.capacity = capacity;
    this.bookings = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }

  public List<Booking> getBookings() {
    return bookings;
  }

  public void setBookings(List<Booking> bookings) {
    this.bookings = bookings;
  }

  @Override
  public String toString() {
    return "Room{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", capacity=" + capacity +
        '}';
  }
}
