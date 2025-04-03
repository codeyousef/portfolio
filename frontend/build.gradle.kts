import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
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

    // Add repositories
    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
            }
        }
    
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))

                // Kotlin dependencies
                implementation(kotlin("stdlib-js"))
                implementation(libs.kotlinx.html.js)
                implementation(libs.kotlinx.coroutines.core.js)
                // Use a direct dependency with a version known to work
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

                // Use very basic wrappers directly (not using the catalog)
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.535")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.535")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:1.0.0-pre.535")
                // implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.5-pre.493")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.535")
                
                // Use NPM libraries instead
                implementation(npm("styled-components", "5.3.5"))
                implementation(npm("@emotion/react", "11.9.3"))
                implementation(npm("@emotion/styled", "11.9.3"))

                // Ktor Client dependencies with direct versions
                implementation("io.ktor:ktor-client-core:2.3.12") {
                    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-json")
                }
                implementation("io.ktor:ktor-client-js:2.3.12") {
                    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-json")
                }
                implementation("io.ktor:ktor-client-content-negotiation:2.3.12") {
                    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-json")
                }
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12") {
                    exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-serialization-json")
                }

                // Add Three.js for 3D effects (optional)
                implementation(npm("three", libs.versions.npm.three.get()))
            }
        }
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
    // Remove the dependency on the setup tasks
    // dependsOn("setupSourceDirs", "setupResourceDirs")
} 