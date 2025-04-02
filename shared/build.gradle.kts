plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.0.21"
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
    }
    js(IR) {
        browser()
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
            }
        }
        
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.6.2")
            }
        }
        
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}

// Make sure JVM target sources are set up correctly
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Task to copy files from old structure to new structure if needed
tasks.register<Copy>("migrateMainToCommon") {
    from("src/main/kotlin")
    into("src/commonMain/kotlin")
    // Only copy if source exists and has files
    onlyIf {
        file("src/main/kotlin").exists() && file("src/main/kotlin").list()?.isNotEmpty() ?: false
    }
}

// Always run migration before compilation
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("migrateMainToCommon")
    kotlinOptions {
        jvmTarget = "17"
    }
} 