# fseRESTApi
### Running the app
```bash
$ gradle bootRun
```

## Endpoints (API-Design)
### Benutzer
- POST /api/users/register – Registrierung eines Benutzers.
- POST /api/users/login – Login und JWT-Generierung.
- GET /api/users/{id} – Benutzerdetails abrufen (nur Admin oder eigener Benutzer).
- DELETE /api/users/{id} – Benutzer löschen (nur Admin).

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