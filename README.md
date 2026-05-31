# MediTrack - Clinic & Appointment Management System

MediTrack is a Spring Boot REST backend for clinic management. The original `DataStore<T>` remains as an OOP learning artifact, while the backend stores doctors, patients, appointments, and bills in a database through Spring Data JPA repositories and Flyway migrations.

## Stack

- Java 17
- Spring Boot Web, Validation, Data JPA
- PostgreSQL for local development and production
- H2 profile for quick local checks
- Flyway for versioned schema migrations
- Swagger UI at `/swagger-ui.html`

## PostgreSQL and pgAdmin setup

pgAdmin is only the administration UI. You also need a running PostgreSQL server service.

1. Open pgAdmin and create the master password when prompted.
2. In the left sidebar, expand `Servers`. If you already see a local PostgreSQL server, connect to it with the password you chose during PostgreSQL installation.
3. If no server appears, right-click `Servers`, choose `Register > Server`, and use:
   - Name: `Local PostgreSQL`
   - Host: `localhost`
   - Port: `5432`
   - Maintenance database: `postgres`
   - Username: `postgres`
   - Password: your PostgreSQL server password
4. Open `Tools > Query Tool` on the `postgres` database and run:

```sql
CREATE ROLE meditrack WITH LOGIN PASSWORD 'meditrack';
CREATE DATABASE meditrack OWNER meditrack;
```

5. Run the app from the project root:

```powershell
mvn spring-boot:run
```

The default `dev` profile connects to:

```text
jdbc:postgresql://localhost:5432/meditrack
username: meditrack
password: meditrack
```

Flyway creates the tables automatically. Do not create the application tables manually in pgAdmin.

## Configuration

Use environment variables when your local credentials differ:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/meditrack"
$env:DB_USERNAME="meditrack"
$env:DB_PASSWORD="your_password"
mvn spring-boot:run
```

Run with H2 instead of PostgreSQL:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

H2 console:

```text
http://localhost:8080/h2-console
```

## API

- `GET /api/doctors`
- `POST /api/doctors`
- `GET /api/patients`
- `POST /api/patients`
- `GET /api/appointments`
- `POST /api/appointments`
- `PATCH /api/appointments/{id}/cancel`
- `POST /api/appointments/{id}/pay`

## Project structure

- `entity/` contains JPA entities and domain behavior.
- `repository/` contains Spring Data JPA repositories.
- `service/` contains transactional business use cases.
- `controller/` exposes REST endpoints.
- `db/migration/` contains Flyway SQL migrations.
- `util/DataStore.java` remains as a learning-only in-memory store.
