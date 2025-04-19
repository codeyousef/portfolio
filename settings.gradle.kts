pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("io.quarkus") version "3.22.0.CR1"
        kotlin("jvm") version "2.2.0-Beta1"
        kotlin("plugin.allopen") version "2.2.0-Beta1"
        kotlin("plugin.serialization") version "2.2.0-Beta1"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    
    versionCatalogs {
        create("libs") {
            // Versions
            version("kotlin", "2.2.0-Beta1")
            version("quarkus", "3.22.0.CR1")
            version("kotlinx-html", "0.8.0")
            version("bcrypt", "0.9.0")
            
            // Libraries
            library("kotlinx-html-jvm", "org.jetbrains.kotlinx", "kotlinx-html-jvm").versionRef("kotlinx-html")
            library("bcrypt", "at.favre.lib", "bcrypt").versionRef("bcrypt")
            
            // Plugins
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-allopen", "org.jetbrains.kotlin.plugin.allopen").versionRef("kotlin")
            plugin("kotlin-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("quarkus", "io.quarkus").versionRef("quarkus")
        }
    }
}

rootProject.name = "portfolio" 