# JVM Report

## Environment

- Java version: 17+
- Runtime: OpenJDK or Oracle JDK
- Platform: Windows

## Memory and performance

The application is lightweight and persists data in PostgreSQL through Spring Data JPA. No heap tuning is required for local development.

## Code characteristics

- Uses `java.time` for date handling.
- Streams are used in `DataStore.search` and appointment filtering.
- Database access is transactional at the service layer.
- Flyway owns schema creation and migrations.

## Build and execution

Build and run with Maven:

```bash
mvn spring-boot:run
```

## Observations

The design reuses Spring-managed services and repositories, with query indexes added for common lookup paths.
