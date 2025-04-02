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
