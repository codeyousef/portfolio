plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
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
                implementation(libs.kotlinx.coroutines.core)
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation(libs.kotlinx.datetime)
            }
        }
        
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(libs.kotlinx.datetime.jvm)
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