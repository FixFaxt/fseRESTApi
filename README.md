# fseRESTApi
### Running the app
```bash
$ gradle bootRun
```

## Endpoints (API-Design)
### Benutzer
- POST /api/register – Register a new User
- POST /api/admin/register - Register a new admin User
- GET /api/user/{id} – Retrieve user details
- DELETE /api/admin/users/{id} – Delete User

### Räume
- GET /api/rooms – Liste aller Räume mit optionalen Filtern (Verfügbarkeit, Ausstattung, Kapazität).
- GET /api/rooms/{id} – Details eines bestimmten Raums.
- POST /api/rooms – Neuen Raum erstellen (nur Admin).
- PUT /api/rooms/{id} – Raumdaten aktualisieren (nur Admin).
- DELETE /api/rooms/{id} – Raum löschen (nur Admin).

### Buchungen
- GET /api/bookings – Liste aller Buchungen (Admins sehen alle, Benutzer nur eigene).
- POST /api/bookings – Neue Buchung erstellen.
- PUT /api/bookings/{id} – Buchung bearbeiten (z. B. Zeit ändern, falls verfügbar).
- DELETE /api/bookings/{id} – Buchung stornieren.
- GET /api/bookings/room/{roomId} – Alle Buchungen für einen Raum. 

## Authorization with Postman
### No current user
- Use "No Auth" option in "Authorization"-Tab
- POST /api/register - Body with user data

### With existing user
- Use "Basic Auth" option in "Authorization"-Tab
- Enter Username (Email) and Password for current user
- Depending on the role you will have access to different endpoints