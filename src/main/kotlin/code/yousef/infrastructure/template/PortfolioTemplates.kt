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
                div(classes = "hero-content fade-in") {
                    h1 { +"Crafting Digital Experiences With Cutting-Edge Tech" }
                    p {
                        +"Software developer specializing in Kotlin, Quarkus, and modern web technologies. Let's build the future together."
                    }
                    div {
                        a(href = "#projects", classes = "cta-button") { +"View Projects" }
                        a(href = "#contact", classes = "cta-button secondary") { +"Get In Touch" }
                    }
                }

                div(classes = "hero-glow") {}
                div(classes = "hero-glow-2") {}
            }

            // Content divider
            div(classes = "content-divider")

            // Projects section
            section(classes = "section") {
                id = "projects"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Featured Projects" }
                    p(classes = "section-desc") {
                        +"Explore my latest work showcasing innovative solutions and cutting-edge technologies."
                    }
                }

                div(classes = "projects-container") {
                    // Add projects directly since we already have them
                    unsafe { +buildProjectsSection(projectEntities) }
                }
            }

            // Content divider
            div(classes = "content-divider")

            // Skills section
            section(classes = "section") {
                id = "skills"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Technical Skills" }
                    p(classes = "section-desc") { +"Advanced technologies powering my development stack" }
                }

                div(classes = "tech-circuit") {
                    // Circuit lines and nodes
                    div(classes = "circuit-lines") {
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}

                        // Circuit nodes
                        div(classes = "circuit-node") { attributes["style"] = "top: 35%; left: 20%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 50%; left: 50%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 65%; left: 80%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 25%; left: 60%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 75%; left: 40%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 55%; left: 10%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 15%; left: 35%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 85%; left: 70%;" }
                    }

                    // Skill cards
                    div(classes = "skill-cards-container") {
                        // Kotlin
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    img(
                                        src = "https://play-lh.googleusercontent.com/jf-wUvZHEVzkyRSiCQiv1IxGsQ4bi0FeKYpVAb5hqkgIIGJqRTGSQ87Q_hbuCJgTsIQ",
                                        alt = "Kotlin",
                                        classes = "skill-logo"
                                    ) {}
                                    h3(classes = "skill-title") { +"Kotlin" }
                                    p(classes = "skill-subtitle") { +"Modern JVM Language" }
                                }

                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"Kotlin" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"Concise, safe, and expressive" }
                                        li(classes = "skill-detail-item") { +"Coroutines for asynchronous code" }
                                        li(classes = "skill-detail-item") { +"100% interoperable with Java" }
                                        li(classes = "skill-detail-item") { +"Backed by JetBrains & Google" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Contact section
            section(classes = "section") {
                id = "contact"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Get In Touch" }
                    p(classes = "section-desc") {
                        +"Have a project in mind? Let's discuss how we can work together to bring your ideas to life."
                    }
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
     * Applies inline styles to ensure grid layout works regardless of CSS conflicts
     */
    fun buildProjectsSection(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div {
            // Apply inline styles to ensure grid layout works correctly
            attributes["class"] = "projects-grid"
            attributes["style"] =
                "display: grid; grid-template-columns: repeat(auto-fit, minmax(350px, 1fr)); gap: 40px; padding: 20px 0;"

            projectEntities.forEach { project ->
                div(classes = "project-card") {
                    // Project image
                    div(classes = "project-image") {
                        img(
                            src = "https://placehold.co/600x400/020024/00f7ff?text=${project.title}",
                            alt = project.title
                        ) {
                            attributes["loading"] = "lazy"
                        }
                    }

                    // Project content
                    div(classes = "project-content") {
                        h3(classes = "project-title") { +project.title }
                        p(classes = "project-desc") { +project.description }

                        div(classes = "tech-stack") {
                            project.technologies.forEach { tech ->
                                span(classes = "project-tag") { +tech }
                            }
                        }

                        div(classes = "project-links") {
                            a(href = project.githubUrl, target = "_blank", classes = "project-link") {
                                +"Github"
                            }
                            a(href = project.demoUrl, target = "_blank", classes = "project-link") {
                                +"Live Demo"
                            }
                        }
                    }
                }
            }
        }
        return writer.toString()
    }
}