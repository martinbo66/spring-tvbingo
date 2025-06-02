# TV Bingo

A Spring Boot application for managing and playing TV show-themed bingo games. The backend provides RESTful APIs for show management, bingo card generation, game state tracking, and user authentication.

---

## Features

- **TV Show Bingo Management**: Create, update, and delete TV show bingo games with custom phrases.
- **Dynamic Bingo Card Generation**: Generates 5x5 bingo cards with unique phrases per show.
- **Game State API**: Track and update the state of bingo games, including marking squares and win detection.
- **User Authentication**: Supports user registration, login, and JWT-based authentication.
- **Guest Play**: Allows anonymous users to play with temporary sessions.
- **Database Migrations**: Uses Liquibase for schema management and seed data.
- **OpenAPI Documentation**: Full API spec available via Swagger UI.
- **Performance Monitoring**: Spring Boot Actuator endpoints for health and metrics.

---

## Getting Started

### Prerequisites
- Java 21+
- Gradle (wrapper included)
- PostgreSQL (local or remote)

### Clone the Repository
```sh
git clone <this-repo-url>
cd spring-tvbingo
```

### Environment Variables
Create a `.envrc` file (or use `.envrc.template` as a starting point) with your database credentials:

```sh
export TVBINGO_DB_USERNAME=tvbingo_user
export TVBINGO_DB_PASSWORD=your_secure_password_here
export TVBINGO_DB_URL=jdbc:postgresql://localhost:5432/tvbingo?currentSchema=tvbingo_schema
```

> **Note:** The application expects the schema `tvbingo_schema` to exist or will create it via Liquibase migrations.

### Database Setup
1. Ensure PostgreSQL is running and accessible.
2. Create a database named `tvbingo` (or adjust the URL as needed).
3. The schema and tables will be created automatically on first run via Liquibase.

### Running the Application

```sh
./gradlew bootRun
```

The app will start on [http://localhost:8080](http://localhost:8080).

---

## API Documentation

- **OpenAPI/Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI Spec**: See [`src/main/resources/openapi.yaml`](src/main/resources/openapi.yaml)

### Example Endpoints
- `GET /api/shows` — List all shows
- `POST /api/shows` — Create a new show
- `GET /api/shows/{id}` — Get show details
- `PUT /api/shows/{id}` — Update a show
- `DELETE /api/shows/{id}` — Delete a show

See the OpenAPI spec for full details on request/response formats and error handling.

---

## Database Schema & Migrations

- **Liquibase** is used for schema management.
- Changelogs are in [`src/main/resources/db/changelog/`](src/main/resources/db/changelog/)
- Initial schema includes a `shows` table with unique constraints and seed data for several TV shows.

---

## Main Dependencies
- Spring Boot 3.2+
- Spring Data JDBC
- Spring Security (JWT)
- Liquibase
- PostgreSQL
- Springdoc OpenAPI
- Lombok
- Embedded Postgres (for tests)

---

## Development & Testing

- Run tests: `./gradlew test`
- Test database uses embedded Postgres for isolation.
- See [HELP.md](HELP.md) for additional Spring/Gradle resources.

---

## Project Structure

- `src/main/java/org/bomartin/tvbingo/` — Main application code
- `src/main/resources/` — Config, migrations, OpenAPI spec
- `tasks/` — Taskmaster AI-generated tasks and documentation

---

## Contributing

Pull requests and issues are welcome! Please ensure new features are covered by tests and documented in the OpenAPI spec.

---

## License

[Apache 2.0](LICENSE)