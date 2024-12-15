# Fortgeschrittene Softwareentwicklung
### Projekt: 
Spring Backend für ein Raumbuchungstool
### Gruppe: 
Fuchs & Laugsch

## Running the app
```bash
$ gradle bootRun
```

## Endpoints

### Rooms
- GET /api/rooms – List of all rooms
- GET /api/rooms/room – Get details of a specific room (with query params)
  - name (String, optional): Name of the room to retrieve
  - id (UUID, optional): ID of the room to retrieve
- POST /api/rooms – Create a new room
- PUT /api/rooms/{id} – Update room data
- PATCH /api/rooms/{id} – Update room data (partially)
- DELETE /api/rooms/{id} – Delete room

### Bookings
- GET /api/bookings – List of all bookings
- POST /api/bookings – Create new booking
- GET /api/bookings/{id} – Get details of specific booking
- PUT /api/bookings/{id} – Update booking data
- PATCH /api/bookings/{id} – Update booking data (partially)
- DELETE /api/bookings/{id} – Delete booking
- GET /api/bookings/room/{roomId} – Get all bookings for a room
