rootProject.name = "portfolio"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        kotlin("jvm") version "2.0.21"
        kotlin("plugin.allopen") version "2.0.21"
        kotlin("multiplatform") version "2.0.21"
        kotlin("plugin.serialization") version "2.0.21"
        id("io.quarkus") version "3.21.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
        mavenLocal()
        // Additional repositories to find kotlinx serialization
        google()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev") }
        maven { url = uri("https://repo.mvnpm.org/maven2") }
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
        
        // Specific kotlin-wrappers repository
        maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/wrappers/kotlin-wrappers-pre-releases") }
        
        // For Node.js
        ivy {
            name = "Node.js"
            url = uri("https://nodejs.org/dist")
            patternLayout {
                artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeModule("org.nodejs", "node")
            }
        }

        // For Yarn
        ivy {
            name = "Yarn"
            url = uri("https://github.com/yarnpkg/yarn/releases/download")
            patternLayout {
                artifact("v[revision]/[artifact](-v[revision]).[ext]")
            }
            metadataSources {
                artifact()
            }
            content {
                includeModule("com.yarnpkg", "yarn")
            }
        }
    }
    
    // Define version catalogs
    versionCatalogs {
        // Define libs catalog directly (instead of from TOML)
        create("libs") {
            // Versions
            version("kotlin", "2.0.21")
            version("kotlinSerialization", "2.0.21")
            version("quarkus", "3.21.0")
            version("kotlinx-html", "0.8.1")
            version("kotlinx-coroutines", "1.8.1-Beta")
            version("kotlinx-datetime", "0.6.0")
            version("ktor", "2.3.12")
            version("bcrypt", "0.10.2")
            
            // Libraries
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("kotlinx-coroutines")
            library("kotlinx-coroutines-core-js", "org.jetbrains.kotlinx", "kotlinx-coroutines-core-js").versionRef("kotlinx-coroutines")
            library("kotlinx-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlinSerialization")
            library("kotlinx-html-js", "org.jetbrains.kotlinx", "kotlinx-html-js").versionRef("kotlinx-html")
            library("kotlinx-html-jvm", "org.jetbrains.kotlinx", "kotlinx-html-jvm").versionRef("kotlinx-html")
            library("kotlinx-datetime", "org.jetbrains.kotlinx", "kotlinx-datetime").versionRef("kotlinx-datetime")
            
            // Ktor
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-js", "io.ktor", "ktor-client-js").versionRef("ktor")
            library("ktor-client-contentnegotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor-serialization-kotlinx-json", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            
            // Other JVM
            library("bcrypt", "at.favre.lib", "bcrypt").versionRef("bcrypt")
            
            // Plugins
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-allopen", "org.jetbrains.kotlin.plugin.allopen").versionRef("kotlin")
            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("kotlin-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlinSerialization")
            plugin("quarkus", "io.quarkus").versionRef("quarkus")
        }
        
        // Define kotlinWrappers catalog with a very stable version
        create("kotlinWrappers") {
            version("wrappers", "pre.535")
            library("react", "org.jetbrains.kotlin-wrappers", "kotlin-react").versionRef("wrappers")
            library("reactDom", "org.jetbrains.kotlin-wrappers", "kotlin-react-dom").versionRef("wrappers")
            library("emotion", "org.jetbrains.kotlin-wrappers", "kotlin-emotion").versionRef("wrappers")
            library("reactRouterDom", "org.jetbrains.kotlin-wrappers", "kotlin-react-router-dom").versionRef("wrappers")
            // Additional wrappers needed by your project
            library("browser", "org.jetbrains.kotlin-wrappers", "kotlin-browser").versionRef("wrappers")
            library("reactCore", "org.jetbrains.kotlin-wrappers", "kotlin-react-core").versionRef("wrappers")
        }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":backend")
include(":shared")
include(":frontend") 