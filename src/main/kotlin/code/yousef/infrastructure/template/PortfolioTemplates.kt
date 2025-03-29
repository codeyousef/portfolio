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
            .data("title", "Portfolio - Projects")
            .data("content", contentHtml)
    }

    /**
     * Builds the home page content using Kotlin HTML DSL
     */
    private fun buildHomeContent(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().section(classes = "hero") {
            h2 { +"Featured Projects" }
            div(classes = "projects-container") {
                // Add projects directly since we already have them
                unsafe { +buildProjectsSection(projectEntities) }
            }
        }
        return writer.toString()
    }

    /**
     * Builds the projects section using Kotlin HTML DSL
     */
    fun buildProjectsSection(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div(classes = "projects-container") {
            projectEntities.forEach { project ->
                div(classes = "project-card") {
                    // Preserve HTMX attributes for dynamic loading
                    attributes["hx-get"] = "/api/projects/${project.id}"
                    attributes["hx-trigger"] = "click"
                    attributes["hx-target"] = "#project-detail"
                    attributes["hx-swap"] = "innerHTML"

                    div(classes = "card-inner") {
                        // Front side
                        div(classes = "card-front glass-morphic") {
                            h3(classes = "glowing-text") { +project.title }
                            p { +project.description }

                            // Tech stack pills
                            div(classes = "tech-stack") {
                                project.technologies.forEach { tech ->
                                    span(classes = "tech-pill") { +tech }
                                }
                            }
                        }

                        // Back side with 3D model
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