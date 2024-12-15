package main.fseRESTApi.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  @NotNull(message = "Start time must not be null")
  private LocalDateTime startTime;

  @Column(nullable = false)
  @NotNull(message = "End time must not be null")
  private LocalDateTime endTime;

  @Column(nullable = false)
  @NotBlank(message = "Email name must not be empty")
  private String email;

  @ManyToOne
  @JoinColumn(name = "room_name", referencedColumnName = "name", nullable = false)
  @JsonBackReference
  @NotNull(message = "Room must not be null")
  private Room room;

  public Booking() {
  }

  @JsonProperty("roomName")
  public String getRoomName() {
    return room != null ? room.getName() : null;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  @Override
  public String toString() {
    return "Booking{" +
        "id=" + id +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", email=" + email +
        ", room=" + room +
        '}';
  }
}
