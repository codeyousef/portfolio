import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.0.21"
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
                // Enable source maps for debugging
                sourceMaps = true
            }
            binaries.executable()

            // Development configuration
            webpackTask {
                devServerProperty = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableListOf(
                        KotlinWebpackConfig.DevServer.Proxy(
                            mutableListOf(
                                "/api",
                                "/blog",
                                "/projects",
                                "/services",
                                "/about",
                                "/contact"
                            ), "http://localhost:8080"
                        ),
                    ),
                    static = mutableListOf("${layout.buildDirectory}/distributions")
                )
            }
        }
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))

                // Kotlin dependencies
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.8.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

                // Kotlin CSS
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.630")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css-js:1.0.0-pre.630")

                // Add Three.js for 3D effects (optional)
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

// Create META-INF/resources directory if it doesn't exist
tasks.register("ensureMetaInfResourcesDir") {
    doLast {
        val resourcesDir = rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources")
        mkdir(resourcesDir)
    }
}

// Add a task to copy the compiled JS output to the Quarkus META-INF/resources directory
tasks.register<Copy>("copyJsToQuarkus") {
    mustRunAfter("jsBrowserProductionWebpack", "ensureMetaInfResourcesDir")

    from(layout.buildDirectory.dir("kotlin-webpack/js/productionExecutable"))
    into(rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources"))

    // Clean up before copying
    doFirst {
        delete(fileTree(rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources")) {
            include("*.js")
            include("*.js.map")
        })
    }
}

// Add a development version of copyJsToQuarkus that uses the development build instead
tasks.register<Copy>("copyJsDevToQuarkus") {
    dependsOn("jsBrowserDevelopmentWebpack", "ensureMetaInfResourcesDir")

    from(layout.buildDirectory.dir("kotlin-webpack/js/developmentExecutable"))
    into(rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources"))

    // Clean up before copying
    doFirst {
        delete(fileTree(rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources")) {
            include("*.js")
            include("*.js.map")
        })
    }
}

// Task to watch for changes and automatically rebuild
tasks.register("watchFrontend") {
    dependsOn("copyJsDevToQuarkus")
    group = "application"
    description = "Watches for frontend changes and automatically copies to Quarkus"

    doLast {
        println("Watching for changes in frontend...")
    }

    // Set up file watching using Gradle's built-in file watching
    inputs.files(fileTree("src") {
        include("**/*.kt")
        include("**/*.js")
        include("**/*.css")
        include("**/*.html")
    }).withPathSensitivity(PathSensitivity.RELATIVE)

    // When changes are detected, trigger the copy task
    outputs.dir(rootProject.layout.projectDirectory.dir("backend/src/main/resources/META-INF/resources"))

    // This is needed to make this task always run, even if no changes
    outputs.upToDateWhen { false }
}

// Make sure KotlinJS compilation happens before the copyJsToQuarkus task
tasks.named("jsBrowserProductionWebpack") {
    dependsOn("jsDevelopmentExecutableCompileSync")
}

tasks.named("compileKotlinJs") {
    dependsOn("setupSourceDirs", "setupResourceDirs")
} 