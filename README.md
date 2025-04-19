# Portfolio Project

A modern portfolio website built with Quarkus backend and @summon UI framework.

## Architecture

This project follows clean architecture principles with a clear separation of concerns:

- **Backend**: Quarkus-based REST API with reactive programming
- **Frontend**: @summon UI framework for Kotlin-based web development
- **Database**: PostgreSQL for data persistence

## Features

- Portfolio showcase with projects, services, and blog
- Admin panel for content management
- Reactive API endpoints
- Responsive design with @summon's CSS utilities
- SEO-friendly pages

## Tech Stack

- **Backend**:
  - Quarkus
  - Reactive PostgreSQL client
  - Panache for database operations
  - Kotlin
  - JWT for authentication

- **Frontend**:
  - @summon UI framework
  - 100% Kotlin-based frontend
  - Type-safe UI components
  - Reactive state management

## Prerequisites

- JDK 17 or later
- Gradle 7.5+
- PostgreSQL database
- @summon JAR in your local Maven repository

## Getting Started

### Development Setup

1. Clone the repository:
   ```
   git clone https://github.com/yourgithub/portfolio.git
   cd portfolio
   ```

2. Setup PostgreSQL database (Docker recommended):
   ```
   docker run --name portfolio-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=portfolio -p 5432:5432 -d postgres
   ```

3. Run the application in development mode:
   ```
   ./gradlew quarkusDev
   ```

This will start the Quarkus backend with the @summon frontend in development mode. The application will be available at http://localhost:8080.

### Building for Production

To build the project for production:

```
./gradlew build -Dquarkus.package.type=uber-jar
```

The resulting jar will be in `build/quarkus-app/`.

## Project Structure

```
portfolio/
├── src/
│   ├── main/
│   │   ├── kotlin/          # Kotlin source code
│   │   │   └── code/yousef/
│   │   │       ├── api/     # REST API endpoints
│   │   │       ├── model/   # Domain models
│   │   │       ├── ui/      # @summon UI components
│   │   │       ├── repository/ # Data repositories
│   │   ├── resources/       # Configuration and static resources
│   ├── test/                # Test sources
├── build.gradle.kts         # Gradle build script
└── settings.gradle.kts      # Gradle settings
```

## API Documentation

The REST API documentation is available at `/q/swagger-ui` when running the application.

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.