plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            binaries.executable()
        }
    }
    
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                
                // Basic Kotlin dependencies
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
                
                // Add Three.js
                implementation(npm("three", "0.169.0"))
            }
        }
    }
}

// Create proper directory structure if needed
tasks.register<Copy>("setupSourceDirs") {
    from("src/main/kotlin")
    into("src/jsMain/kotlin")
    // Only copy if source exists and destination doesn't
    onlyIf {
        file("src/main/kotlin").exists() && !file("src/jsMain/kotlin").exists()
    }
}

tasks.register<Copy>("setupResourceDirs") {
    from("src/main/resources")
    into("src/jsMain/resources")
    // Only copy if source exists and destination doesn't
    onlyIf {
        file("src/main/resources").exists() && !file("src/jsMain/resources").exists()
    }
}

tasks.named("compileKotlinJs") {
    dependsOn("setupSourceDirs", "setupResourceDirs")
} 