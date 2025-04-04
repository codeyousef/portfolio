plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.allopen") version "2.0.21"
    id("io.quarkus") version "3.21.0"
    kotlin("plugin.serialization") version "2.0.21"
}

group = "code.yousef"
version = "1.0.0-SNAPSHOT"

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

// Explicitly define source sets for Quarkus to find them
sourceSets {
    main {
        java.srcDirs("src/main/kotlin")
        resources.srcDirs("src/main/resources")
    }
    test {
        java.srcDirs("src/test/kotlin")
        resources.srcDirs("src/test/resources")
    }
}

// Configure Quarkus extension
quarkus {
    // Set the final name (important for the uber-jar)
    finalName = "${project.name}-${project.version}"
}

// Handle duplicate resources
tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    
    // Core Quarkus dependencies
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")

    // Database
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    
    // Security
    implementation("io.quarkus:quarkus-security-jpa-reactive")
    
    // Templates
    implementation("io.quarkus:quarkus-qute")
    
    // Web Bundler for Vue.js integration
    implementation("io.quarkiverse.web-bundler:quarkus-web-bundler:1.8.1")
    
    // Kotlin libraries
    implementation("io.vertx:vertx-lang-kotlin-coroutines")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
    implementation("at.favre.lib:bcrypt:0.9.0")
    
    // Testing
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        javaParameters = true
    }
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

// Initialize yarn lock before running development tasks
tasks.register("initializeYarnLock") {
    description = "Initialize and upgrade yarn lock to avoid lock file issues"
    group = "application"
    
    doFirst {
        logger.lifecycle("Initializing yarn lock...")
    }
    
    dependsOn("quarkusGenerateCode")
}

// Convenience task to build and run the full application
tasks.register("devWithFrontend") {
    dependsOn("initializeYarnLock", "quarkusDev")
    description = "Runs the application with Quarkus dev mode and web bundler"
    group = "application"
}

// Continuous development task that watches for frontend changes while running Quarkus
tasks.register("devWatch") {
    dependsOn("initializeYarnLock", "quarkusDev")
    description = "Runs the application with Quarkus dev mode and web bundler"
    group = "application"
    
    doFirst {
        println("Starting continuous development mode...")
        println("Frontend will be automatically compiled and served by Quarkus Web Bundler")
        println("Access the application at http://localhost:8080")
        println("Ctrl+C to stop")
    }
}

// Custom Vue.js dev server task
tasks.register<Exec>("vueDevServer") {
    description = "Runs the Vue.js dev server separately for optimal hot reloading"
    group = "application"
    
    dependsOn("quarkusGenerateCode")
    
    // Set working directory to the Vue project
    workingDir = file("${projectDir}/src/main/resources/web")
    
    // Define the command to run
    if (org.gradle.internal.os.OperatingSystem.current().isWindows) {
        commandLine("cmd", "/c", "yarn", "dev")
    } else {
        commandLine("yarn", "dev")
    }
    
    // Better error output
    standardOutput = System.out
    errorOutput = System.err
    
    doFirst {
        println("Starting Vue.js dev server...")
        println("Access the frontend at http://localhost:5173")
        println("Changes to Vue files will be hot-reloaded automatically")
        println("API calls will be proxied to the Quarkus server")
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
        println("Terminal 1: ./gradlew quarkusDev")
        println("Terminal 2: ./gradlew vueDevServer")
        println("")
        println("Or use npm scripts in two separate terminals:")
        println("Terminal 1: npm run dev:api")
        println("Terminal 2: npm run dev:ui")
    }
}

// Define the quarkusDevHotApi task 
tasks.register("quarkusDevHotApi") {
    description = "Runs Quarkus in dev mode with API focus (pair with vueDevServer)"
    group = "application"
    
    // Depend on the quarkusDev task
    dependsOn("quarkusDev")
}
