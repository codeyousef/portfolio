package code.yousef.pages

import code.yousef.api.ProjectsApi
import code.yousef.api.ServicesApi
import code.yousef.components.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.html.*
import kotlinx.html.dom.append
import org.w3c.dom.HTMLElement

/**
 * Home page component
 */
class HomePage(private val container: HTMLElement) {
    init {
        render()
    }

    /**
     * Render the home page
     */
    private fun render() {
        // Initial render with loading state
        container.append {
            div(classes = "hero") {
                div(classes = "container") {
                    div(classes = "hero-content") {
                        h1 { +"Crafting Digital Experiences With Cutting-Edge Tech" }
                        p { +"Software developer specializing in Kotlin, Quarkus, and modern web technologies. Let's build the future together." }
                        div(classes = "hero-actions") {
                            a(href = "#projects", classes = "button") { +"View Projects" }
                            a(href = "/contact", classes = "button") {
                                style = "background-color: transparent; border: 1px solid var(--primary); color: var(--primary);"
                                +"Get In Touch"
                            }
                        }
                    }
                }
            }

            section(classes = "section") {
                id = "projects"
                div(classes = "container") {
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Featured Projects" }
                        p { +"Explore my latest work showcasing innovative solutions and cutting-edge technologies." }
                    }

                    // Projects will be loaded dynamically
                    div(classes = "projects-grid") {
                        id = "projects-grid"
                        // Loading state
                        div(classes = "loading") {
                            +"Loading projects..."
                        }
                    }
                }
            }

            section(classes = "section") {
                div(classes = "container") {
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Services" }
                        p { +"Professional services tailored to your business needs." }
                    }

                    // Services will be loaded dynamically
                    div(classes = "services-grid") {
                        id = "services-grid"
                        // Loading state
                        div(classes = "loading") {
                            +"Loading services..."
                        }
                    }
                }
            }
        }

        // Load data
        loadData()
    }

    /**
     * Load data from API
     */
    private fun loadData() {
        MainScope().launch {
            // Load projects and services in parallel
            val projectsDeferred = async { ProjectsApi.getFeaturedProjects() }
            val servicesDeferred = async { ServicesApi.getFeaturedServices() }

            try {
                val projects = projectsDeferred.await()
                val services = servicesDeferred.await()

                // Render projects
                val projectsGrid = document.getElementById("projects-grid")
                projectsGrid?.innerHTML = ""

                projectsGrid?.append {
                    projects.forEach { project ->
                        div(classes = "card") {
                            if (project.imageUrl.isNotEmpty()) {
                                img(src = project.imageUrl, alt = project.title, classes = "card-image")
                            }

                            div(classes = "card-content") {
                                h3(classes = "card-title") { +project.title }
                                p(classes = "card-description") { +project.description }

                                if (project.technologies.isNotEmpty()) {
                                    div(classes = "tags") {
                                        project.technologies.forEach { tech ->
                                            span(classes = "tag") { +tech }
                                        }
                                    }
                                }

                                a(href = "/projects/${project.id}", classes = "button") {
                                    +"View Project"
                                }
                            }
                        }
                    }

                    if (projects.isEmpty()) {
                        div { +"No projects found." }
                    }
                }

                // Render services
                val servicesGrid = document.getElementById("services-grid")
                servicesGrid?.innerHTML = ""

                servicesGrid?.append {
                    services.forEach { service ->
                        div(classes = "card") {
                            div(classes = "card-content") {
                                h3(classes = "card-title") { +service.title }
                                p(classes = "card-description") { +service.shortDescription }

                                if (service.features.isNotEmpty()) {
                                    ul {
                                        service.features.take(3).forEach { feature ->
                                            li { +feature }
                                        }
                                    }
                                }

                                a(href = service.detailsLink, classes = "button") {
                                    +service.ctaText
                                }
                            }
                        }
                    }

                    if (services.isEmpty()) {
                        div { +"No services found." }
                    }
                }
            } catch (e: Exception) {
                console.error("Error loading data: $e")

                document.getElementById("projects-grid")?.innerHTML = "Error loading projects."
                document.getElementById("services-grid")?.innerHTML = "Error loading services."
            }
        }
    }
}