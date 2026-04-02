# Yakamon

Backend implementation of a creature-collection game using Java, Quarkus, and Hibernate.

## Description

This project consists of building a RESTful API for a game where a player explores a map, collects resources, and captures creatures called *Yakamons*.

The backend handles:
- player state
- map exploration
- inventory management
- creature capture and evolution
- Yakadex (creature encyclopedia)

The project follows a **layered architecture** (presentation, business, data) :contentReference[oaicite:0]{index=0}  

## ⚠️ Project Status

This project is **partially implemented (~72%)**.

Most core features are functional, but some endpoints or edge cases may be incomplete.

## Tech Stack

- Java
- Quarkus (REST API)
- Hibernate (ORM)
- PostgreSQL
- JUnit (tests)

## Architecture

The project follows a layered architecture:

- **Presentation layer**: Controllers / REST endpoints
- **Business layer**: Services and entities
- **Data layer**: Repositories and models
- **Converters**: Data transformation between layers

This structure ensures modularity and maintainability :contentReference[oaicite:1]{index=1}  

## Build

```bash
mvn clean install
```

## Run

```bash
mvn quarkus:dev
```

API will be available at:

```
http://localhost:8081
```

Test endpoint:

```bash
curl http://localhost:8081/hello
```

## Swagger UI

```bash
http://localhost:8081/q/swagger-ui
```

Allows you to test endpoints easily.

## Database Setup

Example setup (PostgreSQL):

```bash
initdb
postgres -k /tmp
createuser -s postgres
createdb -U postgres yakamon
```

## Features (Implemented)

- Player initialization
- Basic movement
- Resource collection (partial)
- Yakamon capture (partial)
- Inventory management
- Yakadex (partial)

## Features (Incomplete / TODO)

- Some advanced endpoints
- Full error handling
- Edge cases
- Complete test coverage

## Project Structure

```
yakamon/
├── pom.xml
├── src/
│   ├── main/java/fr/epita/assistants/yakamon/
│   │   ├── presentation/
│   │   ├── domain/
│   │   ├── data/
│   │   ├── converter/
│   │   └── utils/
│   └── resources/
└── test/
```

## Testing

```bash
mvn test
```

## Notes

- The project strictly follows the required architecture
- DTOs are only used in the presentation layer
- Services contain business logic only
- Repositories handle database interactions only

## Author

EPITA student project
