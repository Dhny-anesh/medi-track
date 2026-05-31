# Design Decisions

## Architecture

The application uses a layered Spring Boot backend design with domain entities, repositories, services, and REST controllers.

- `entity` contains JPA domain models and relationships.
- `repository` contains Spring Data JPA persistence contracts.
- `service` encapsulates transactional business operations.
- `controller` exposes HTTP endpoints.
- `util` contains reusable helpers. `DataStore<T>` is kept only as a learning artifact for the OOP module.
- `db/migration` contains Flyway migrations so schema changes are versioned.

## SOLID principles

- Single Responsibility: controllers handle HTTP, services handle use cases, repositories handle persistence, and `DatabaseSeeder` handles local seed data.
- Open/Closed: `Searchable`, `Payable`, and `BillingStrategy` allow future behavior to be added without rewriting core flows.
- Liskov Substitution: `Doctor` and `Patient` extend `Person` safely.
- Interface Segregation: `Searchable` and `Payable` are small, focused contracts.
- Dependency Inversion: services depend on repository interfaces instead of concrete persistence implementation details.

## Error handling

Custom exceptions simplify validation and lookup failures.

## Persistence

PostgreSQL is the default development and production database. H2 is available through the `h2` Spring profile for fast local checks. Hibernate never creates or updates the schema directly; Flyway owns schema migrations.

## Optional features

- `CSVUtil` exports CSV representations of data.
- `AIHelper` provides static guidance strings.
- `Appointment` billing is generated automatically.
