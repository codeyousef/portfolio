pluginManagement {
    val quarkusPluginVersion: String by settings
    val kotlinVersion: String by settings
    val kotlinMultiplatformVersion: String by settings
    val kotlinSerializationVersion: String by settings
    
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
        kotlin("multiplatform") version kotlinMultiplatformVersion
        kotlin("plugin.serialization") version kotlinSerializationVersion
        id("io.quarkus") version quarkusPluginVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
        maven { url = uri("https://repo.mvnpm.org/maven2") }
    }

    versionCatalogs {
        create("kotlinWrappers") {
            val wrappersVersion = "2025.4.0"
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")
        }
    }
}

rootProject.name = "portfolio"

// Include all modules - backend, shared, and frontend
include(":backend")
include(":shared")
include(":frontend")
