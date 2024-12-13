package main.fseRESTApi.api.model.repositories;

import main.fseRESTApi.api.model.Booking;
import main.fseRESTApi.api.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
  List<Booking> findByRoom(Room room);

  List<Booking> findByRoom_Name(String roomName);

  List<Booking> findByEmail(String email);

  List<Booking> findByRoomAndStartTimeBetween(Room room, LocalDateTime start, LocalDateTime end);

  Optional<Booking> findByIdAndRoom(UUID id, Room room);

  @Query("SELECT COUNT(b) > 0 FROM Booking b " +
      "WHERE b.room.id = :roomId " +
      "AND b.startTime < :endTime " +
      "AND b.endTime > :startTime")
  boolean existsOverlappingBooking(@Param("roomId") UUID roomId,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);
}