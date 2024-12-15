package main.fseRESTApi.api.model;

import java.time.LocalDateTime;

public class BookingUpdateDto {

  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String email;
  private Room room;

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public void setStartTime(LocalDateTime starTime) {
    this.startTime = starTime;
  }

  public LocalDateTime getEndTime() {
    return this.endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Room getRoom() {
    return this.room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public String getRoomName() {
    return this.room.getName();
  }
}
