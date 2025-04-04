# Portfolio Project

A modern portfolio website built with Quarkus backend and Vue.js frontend.

## Architecture

This project follows clean architecture principles with a clear separation of concerns:

- **Backend**: Quarkus-based REST API with reactive programming
- **Frontend**: Vue.js with TypeScript and Tailwind CSS
- **Database**: PostgreSQL for data persistence

## Features

- Portfolio showcase with projects, services, and blog
- Admin panel for content management
- Reactive API endpoints
- Responsive design with Tailwind CSS
- SEO-friendly pages

## Tech Stack

- **Backend**:
  - Quarkus
  - Reactive PostgreSQL client
  - Panache for database operations
  - Kotlin
  - JWT for authentication

- **Frontend**:
  - Vue.js 3 with Composition API
  - TypeScript
  - Tailwind CSS for styling
  - Pinia for state management
  - Vue Router for navigation

## Prerequisites

- JDK 17 or later
- Gradle 7.5+
- Node.js 16+ and Yarn (installed automatically by Quarkus Web Bundler)
- PostgreSQL database

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
   ./gradlew devWithFrontend
   ```

This will start the Quarkus backend with the Vue.js frontend in development mode. The application will be available at http://localhost:8080.

### Development with Advanced Hot Reloading

For the best developer experience with hot module reloading for both backend and frontend:

#### Option 1: Using Gradle tasks

Open two terminal windows:

```bash
# Terminal 1 - Run the backend API server
./gradlew :backend:quarkusDevHotApi

# Terminal 2 - Run the Vue.js dev server
./gradlew :backend:vueDevServer
```

#### Option 2: Using npm scripts (Windows)

Open two terminal windows:

```bash
# Terminal 1 - Run the backend API server
npm run dev:api

# Terminal 2 - Run the Vue.js dev server
npm run dev:ui
```

#### Option 3: Using the helper script (Unix/Linux/Mac)

```bash
./start-hot.sh
```

Then access:
- Frontend: http://localhost:5173 (for development)
- Backend API: http://localhost:8080

The Vue.js dev server will automatically proxy API requests to the Quarkus backend.

### Building for Production

To build the project for production:

```
./gradlew build -Dquarkus.package.type=uber-jar
```

The resulting jar will be in `backend/build/quarkus-app/`.

## Project Structure

```
portfolio/
├── backend/               # Backend module
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/    # Kotlin source code
│   │   │   ├── resources/ # Configuration and static resources
│   │   │   │   └── web/   # Vue.js frontend source
│   │   ├── test/          # Test sources
├── build.gradle.kts       # Root build script
└── settings.gradle.kts    # Gradle settings
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