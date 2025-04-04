plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.allopen) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.quarkus) apply false
}

group = "code.yousef"
version = "1.0.0-SNAPSHOT"

// Initialize yarn lock before running development tasks
tasks.register("initializeYarnLock") {
    description = "Initialize and upgrade yarn lock to avoid lock file issues"
    group = "application"
    
    doFirst {
        logger.lifecycle("Initializing yarn lock...")
    }
    
    // Just directly depend on the kotlinUpgradeYarnLock task
    // This makes gradle handle the task dependency properly
    dependsOn(":backend:quarkusGenerateCode")
}

// Convenience task to build and run the full application
tasks.register("devWithFrontend") {
    dependsOn("initializeYarnLock", ":backend:quarkusDev")
    description = "Runs the backend with Quarkus dev mode and web bundler"
    group = "application"
}

// Continuous development task that watches for frontend changes while running Quarkus
tasks.register("devWatch") {
    dependsOn("initializeYarnLock", ":backend:quarkusDev")
    description = "Runs the backend with Quarkus dev mode and web bundler"
    group = "application"
    
    doFirst {
        println("Starting continuous development mode...")
        println("Frontend will be automatically compiled and served by Quarkus Web Bundler")
        println("Access the application at http://localhost:8080")
        println("Ctrl+C to stop")
    }
}

// Advanced development task that runs both Quarkus dev mode and Vue's dev server separately
// This provides the best hot reload experience for both backend and frontend
tasks.register("devHot") {
    description = "Runs both Quarkus dev mode and a separate Vue dev server with hot reloading for both"
    group = "application"
    
    doFirst {
        println("Starting advanced development mode with separate servers...")
        println("This requires two terminal windows:")
        println("1. Backend: Quarkus running on http://localhost:8080")
        println("2. Frontend: Vue dev server running on http://localhost:5173")
        println("")
        println("Use the Vue dev server URL during development for the best hot-reload experience")
        println("The backend API will be proxied automatically from the Vue dev server")
        println("")
        println("Note: To run this task properly, open two terminals and run:")
        println("Terminal 1: ./gradlew :backend:quarkusDevHotApi")
        println("Terminal 2: ./gradlew :backend:vueDevServer")
        println("")
        println("Or use npm scripts in two separate terminals:")
        println("Terminal 1: npm run dev:api")
        println("Terminal 2: npm run dev:ui")
    }
}
