# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Core Technology Stack

- **Spring Boot 3.5+** with Java 21
- **Spring Data JDBC** for database operations
- **PostgreSQL** with Liquibase migrations
- **Gradle** build system with wrapper
- **Lombok** for boilerplate reduction
- **SpringDoc OpenAPI** for API documentation
- **Embedded Postgres** for testing

## Common Development Commands

```bash
# Build and run the application
./gradlew bootRun

# Run all tests with detailed output
./gradlew test

# Build the project
./gradlew build

# Clean build artifacts
./gradlew clean
```

## Database Configuration

The application requires PostgreSQL with these environment variables:
- `TVBINGO_DB_URL` - Database connection URL
- `TVBINGO_DB_USERNAME` - Database username  
- `TVBINGO_DB_PASSWORD` - Database password

Tests use embedded Postgres and require no external database setup.

## Application Architecture

### Package Structure
```
org.bomartin.tvbingo/
├── config/          # Spring configuration (CORS, etc.)
├── controller/      # REST endpoints
├── dto/            # Data Transfer Objects
├── exception/      # Global exception handling
├── model/          # Domain entities
├── repository/     # Data access layer
├── service/        # Business logic
└── validation/     # Custom validators
```

### Key Components

- **Show Entity**: Core domain model with title, phrases, and bingo card data
- **ShowController**: REST API endpoints for CRUD operations on shows
- **ShowService**: Business logic for show management
- **ShowRepository**: Spring Data JDBC repository for database access
- **Liquibase Migrations**: Schema management in `src/main/resources/db/changelog/`

### API Design
- Base path: `/api/shows`
- Full OpenAPI spec available at `/swagger-ui.html` when running
- CORS configured for frontend at `http://localhost:5173`

### Validation Strategy
- Uses Jakarta Bean Validation annotations
- Custom validator `@UniqueShowTitle` for business rules
- Global exception handler in `GlobalExceptionHandler`

### Database Schema
- Primary table: `shows` with JSON column for phrases
- Unique constraint on `show_title`
- Schema managed via Liquibase with `tvbingo_schema`

## Testing Approach

Tests use:
- Embedded Postgres for database integration tests
- Spring Boot Test framework
- Detailed test logging enabled in build.gradle
- Separate test configuration in `application-test.yml`