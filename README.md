# Portfolio Application

A modular portfolio application built with Kotlin, Kotlin/JS, and Quarkus.

## Project Structure

This project is structured as a modular Gradle project with the following modules:

- **backend**: Quarkus backend that serves the API and the frontend static files
- **frontend**: Kotlin/JS frontend application that gets compiled to JavaScript
- **shared**: Shared Kotlin multiplatform module with common code used by both frontend and backend

## Prerequisites

- JDK 17 or higher
- Gradle 8.5 or higher
- PostgreSQL database

## How to Run

### Development Mode

1. Start the PostgreSQL database:
   ```
   docker-compose up -d
   ```

2. Run the application in development mode:
   ```
   ./gradlew :backend:quarkusDev
   ```
   
   This will:
   - Compile the shared module
   - Build the frontend module and output the JS assets
   - Copy the frontend assets to the backend resources directory
   - Start the Quarkus application in dev mode

3. Access the application at http://localhost:8080
   - Main portfolio application: http://localhost:8080
   - Kotlin/JS frontend: http://localhost:8080/app
   - API endpoints: http://localhost:8080/api/v1/projects, /api/v1/services, etc.

### Production Build

To build the application for production:

```
./gradlew build
```

This will produce an uber-jar in the `backend/build/quarkus-app` directory.

To run the production jar:

```
java -jar backend/build/quarkus-app/quarkus-run.jar
```

## Project Architecture

### Shared Module

The shared module contains:
- Common data models that can be used on both frontend and backend
- Models: Project, Service, BlogPost, User
- Shared utility functions and constants

### Frontend Module

The frontend module is a Kotlin/JS application that:
- Uses Kotlin HTML DSL for building the UI
- Communicates with the backend API endpoints
- Gets compiled to JavaScript and static assets
- Is served by the Quarkus backend

### Backend Module

The backend module is a Quarkus application that:
- Provides REST API endpoints that return the shared models as JSON
- Serves the compiled frontend static assets
- Connects to a PostgreSQL database
- Handles authentication and authorization

## Technology Stack

- **Backend**:
  - Quarkus
  - Kotlin
  - Hibernate Reactive with Panache
  - PostgreSQL
  - Jakarta RESTful Web Services (JAX-RS)

- **Frontend**:
  - Kotlin/JS
  - Kotlin HTML DSL
  - CSS
  - Kotlin Coroutines

- **Shared**:
  - Kotlin Multiplatform
  - Kotlinx Serialization

## License

[MIT License](LICENSE)