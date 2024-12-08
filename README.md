# fseRESTApi
### Running the app
```bash
$ gradle bootRun
```

## Endpoints (API-Design)

### Räume
- GET /api/rooms – Liste aller Räume mit optionalen Filtern (Verfügbarkeit, Ausstattung, Kapazität)
- GET /api/rooms/room – Details eines bestimmten Raums. (Query Parameter: name, id)
- POST /api/rooms – Neuen Raum erstellen
- PUT /api/rooms/{id} – Raumdaten aktualisieren
- DELETE /api/rooms/{id} – Raum löschen

### Buchungen
- GET /api/bookings – Liste aller Buchungen
- POST /api/bookings – Neue Buchung erstellen
- PUT /api/bookings/{id} – Buchung bearbeiten (z. B. Zeit ändern, falls verfügbar)
- DELETE /api/bookings/{id} – Buchung stornieren
- GET /api/bookings/room/{roomId} – Alle Buchungen für einen Raum.