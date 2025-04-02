plugins {
    kotlin("jvm") version "2.0.21" apply false
    kotlin("plugin.allopen") version "2.0.21" apply false
    kotlin("js") version "2.0.21" apply false
    kotlin("multiplatform") version "2.0.21" apply false
    kotlin("plugin.serialization") version "2.0.21" apply false
    id("io.quarkus") apply false
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven { url = uri("https://repo.mvnpm.org/maven2") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
    }
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
    dependsOn("kotlinUpgradeYarnLock")
}

// Convenience task to build and run the full application
tasks.register("devWithFrontend") {
    dependsOn("initializeYarnLock", ":frontend:copyJsToQuarkus", ":backend:quarkusDev")
    description = "Compiles the frontend and runs the backend with Quarkus dev mode"
    group = "application"
}

// Continuous development task that watches for frontend changes while running Quarkus
tasks.register("devWatch") {
    dependsOn("initializeYarnLock", ":frontend:watchFrontend", ":backend:quarkusDev")
    description = "Watches for frontend changes and runs the backend with Quarkus dev mode"
    group = "application"
    
    doFirst {
        println("Starting continuous development mode...")
        println("Frontend will be automatically compiled and copied to Quarkus on changes")
        println("Access the application at http://localhost:8080")
        println("Ctrl+C to stop")
    }
}
