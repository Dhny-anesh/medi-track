# Setup Instructions

## Prerequisites

- Java JDK 17 or newer installed.
- Maven available on the command line.
- PostgreSQL server running locally.
- pgAdmin for database administration.

## PostgreSQL setup in pgAdmin

pgAdmin is the GUI. PostgreSQL server must also be installed and running.

1. Open pgAdmin.
2. Connect to your local server under `Servers`.
3. If there is no local server, use `Register > Server`:
   - Host: `localhost`
   - Port: `5432`
   - Maintenance database: `postgres`
   - Username: `postgres`
4. Open `Tools > Query Tool` and run:

```sql
CREATE ROLE meditrack WITH LOGIN PASSWORD 'meditrack';
CREATE DATABASE meditrack OWNER meditrack;
```

The application uses Flyway, so the tables are created automatically on startup.

## Run with PostgreSQL

From the project root:

```powershell
mvn spring-boot:run
```

Default local database settings:

```text
DB_URL=jdbc:postgresql://localhost:5432/meditrack
DB_USERNAME=meditrack
DB_PASSWORD=meditrack
```

Override them in PowerShell when needed:

```powershell
$env:DB_PASSWORD="your_password"
mvn spring-boot:run
```

## Run with H2

Use the H2 profile for quick local checks without PostgreSQL:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

H2 console is available at `/h2-console`.
