# Vue.js Frontend

This is the Vue.js frontend for the Portfolio project. It's built with Vue 3, TypeScript, and Tailwind CSS.

## Structure

The frontend follows a clean architecture with the following structure:

- `src/`
  - `assets/` - Static assets like images and global CSS
  - `components/` - Reusable Vue components
  - `models/` - TypeScript interfaces for data models
  - `router/` - Vue Router configuration
  - `services/` - API services for backend communication
  - `views/` - Page components

## Models

The TypeScript models are defined to match the backend Kotlin data classes:

- `BlogPost` - Blog post model with methods for checking publication status and reading time
- `Project` - Project showcase model
- `Service` - Service offering model
- `User` - User model with role-based access control

## Development

To start the development server:

```bash
# Run from the project root
./gradlew vueDevServer
```

The development server will be available at http://localhost:5173 and will proxy API requests to the Quarkus backend running on port 8080.

## Production Build

The production build is automatically handled by the Quarkus Gradle plugin.

```bash
# Run from the project root
./gradlew build
```

This will build the Vue.js application and include it in the Quarkus application. 