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

  @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM Booking b " +
      "WHERE b.room.name = :roomName " +
      "AND NOT (:startTime >= b.endTime OR :endTime <= b.startTime)" +
      "AND (:excludeId IS NULL OR b.id <> :excludeId)")
  boolean existsOverlappingBooking(@Param("roomName") String roomName,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime,
      @Param("excludeId") UUID excludeId);
}