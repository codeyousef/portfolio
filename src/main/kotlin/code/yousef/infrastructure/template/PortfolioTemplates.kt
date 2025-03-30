package code.yousef.infrastructure.template

import code.yousef.infrastructure.persistence.entity.ProjectEntity
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

@ApplicationScoped
class PortfolioTemplates {

    @Location("base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    /**
     * Builds the home page using Kotlin HTML DSL and wraps it in a Qute template
     */
    fun buildHomePage(projectEntities: List<ProjectEntity>): TemplateInstance {
        // Generate the HTML content using Kotlin DSL
        val contentHtml = buildHomeContent(projectEntities)

        // Use Qute template for the base layout
        return baseTemplate
            .data("title", "Sci-Fi Cyberwave Portfolio")
            .data("content", contentHtml)
    }

    /**
     * Builds the home page content using Kotlin HTML DSL with Sci-Fi Cyberwave theme
     */
    private fun buildHomeContent(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div {
            // Hero section with intro
            section(classes = "hero") {
                id = "home"
                h2(classes = "glowing-text") { +"Welcome to My Cyberwave Portfolio" }
                p { 
                    +"I'm a software developer specializing in Kotlin, Quarkus, and modern web technologies. " 
                    +"Explore my digital space inspired by NEOM's The Line with immersive 3D elements."
                }
                
                // The terminal container is now defined in base-layout.html
            }
            
            // Content divider with light effect
            div(classes = "content-divider")
            
            // Projects section with 3D flip cards
            section(classes = "projects-section") {
                id = "projects"
                h2(classes = "glowing-text") { +"Featured Projects" }
                p {
                    +"Hover over the cards to reveal 3D models and project details. These glass-morphic panels "
                    +"showcase my work with interactive elements."
                }
                
                div(classes = "projects-container") {
                    // Add projects directly since we already have them
                    unsafe { +buildProjectsSection(projectEntities) }
                }
            }
            
            // Content divider
            div(classes = "content-divider")
            
            // Contact section
            section(classes = "contact-section") {
                id = "contact"
                h2(classes = "glowing-text") { +"Contact" }
                p {
                    +"Interested in collaborating on a project? Reach out through the channels below."
                }
                
                div(classes = "contact-container glass-morphic") {
                    div(classes = "contact-item") {
                        span(classes = "contact-icon") { +"📧" }
                        a(href = "mailto:contact@example.com") { +"contact@example.com" }
                    }
                    div(classes = "contact-item") {
                        span(classes = "contact-icon") { +"🔗" }
                        a(href = "https://github.com/yourusername", target = "_blank") { +"GitHub" }
                    }
                    div(classes = "contact-item") {
                        span(classes = "contact-icon") { +"💼" }
                        a(href = "https://linkedin.com/in/yourusername", target = "_blank") { +"LinkedIn" }
                    }
                }
            }
        }
        return writer.toString()
    }

    /**
     * Builds the projects section using Kotlin HTML DSL with glass-morphic cards as specified
     */
    fun buildProjectsSection(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div(classes = "projects-grid") {
            projectEntities.forEach { project ->
                div(classes = "project-card") {
                    // Preserve HTMX attributes for dynamic loading
                    attributes["hx-get"] = "/api/projects/${project.id}"
                    attributes["hx-trigger"] = "click"
                    attributes["hx-target"] = "#project-detail"
                    attributes["hx-swap"] = "innerHTML"

                    div(classes = "card-inner") {
                        // Front side with glowing text as specified
                        div(classes = "project-info") {
                            h3 { +project.title }
                            p { +project.description }

                            div(classes = "tech-stack") {
                                project.technologies.forEach { tech ->
                                    span(classes = "tech-pill") { +tech }
                                }
                            }
                        }

                        // Back side with 3D model as specified
                        div(classes = "card-back glass-morphic") {
                            div(classes = "model-container") {
                                // Container for 3D model
                                attributes["data-model-url"] = project.modelUrl
                            }

                            div(classes = "project-links") {
                                a(href = project.githubUrl, classes = "btn neon-btn", target = "_blank") {
                                    +"View Code"
                                }
                                a(href = project.demoUrl, classes = "btn neon-btn", target = "_blank") {
                                    +"Live Demo"
                                }
                            }
                        }
                    }
                }
            }
        }
        return writer.toString()
    }
}