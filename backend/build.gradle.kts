plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.allopen)
    alias(libs.plugins.quarkus)
}

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

dependencies {
    // Removing shared module dependency
    // implementation(project(":shared", "jvmRuntimeElements"))
    
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-security-jpa-reactive")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-qute")
    
    // Add the Quarkus Web Bundler extension
    implementation("io.quarkus:quarkus-web-bundler")

    implementation("io.vertx:vertx-lang-kotlin-coroutines")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(libs.kotlinx.html.jvm)
    implementation(libs.bcrypt)
    implementation("io.quarkus:quarkus-arc")
    
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

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        javaParameters = true
    }
}

// Removing shared module dependency
// tasks.named("compileKotlin") {
//     dependsOn(":shared:compileKotlinJvm")
// }

// Removing frontend task dependency
// tasks.named("quarkusDev") {
//     dependsOn(":frontend:copyJsToQuarkus")
// }

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

// Custom Quarkus dev task for API only (better for dual server setup)
tasks.register("quarkusDevHotApi") {
    description = "Runs Quarkus in dev mode with API focus (pair with vueDevServer)"
    group = "application"
    
    // Set system properties to disable web bundler auto-start
    val quarkusDevTask = tasks.named("quarkusDev")
    
    // Configure the quarkusDev task
    quarkusDevTask.configure {
        doFirst {
            println("Starting Quarkus in API-focused dev mode...")
            println("Backend API available at http://localhost:8080")
            println("This task is optimized to work with the separate Vue dev server")
        }
    }
    
    // Depend on the quarkusDev task
    dependsOn(quarkusDevTask)
} 