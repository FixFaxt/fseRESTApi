# Fortgeschrittene Softwareentwicklung
### Projekt: 
Spring Backend für ein Raumbuchungstool
### Gruppe: 
Fuchs & Laugsch

## App
### Setup
In application.properties add:
- DB Url
- DB Username
- DB Password

### Running the app
```bash
$ gradle bootRun
```

### Run app tests
```bash
$ gradle test
```
#### Run specific tests
```bash
$ gradle test --tests "RoomServiceTest"
```

```bash
$ gradle test --tests "RoomServiceTest"
```

## Dependencies
- org.springframework.boot:spring-boot-starter-web
- org.springframework.boot:spring-boot-starter-data-jpa
- org.springframework.boot:spring-boot-starter-security
- org.springframework.boot:spring-boot-starter-validation
- org.postgresql:postgresql
- org.springframework.boot:spring-boot-starter-test
- org.junit.platform:junit-platform-launcher

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
