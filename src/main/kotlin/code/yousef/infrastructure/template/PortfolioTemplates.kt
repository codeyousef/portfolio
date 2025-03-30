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
            .data("title", "NeoTech Portfolio")
            .data("content", contentHtml)
    }

    /**
     * Builds the home page content using Kotlin HTML DSL with our new design
     */
    private fun buildHomeContent(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div {
            // Hero Section with code window and terminal
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

                div(classes = "hero-glow") { }
                div(classes = "hero-glow-2") { }

                div(classes = "floating-elements") {
                    // Code window
                    div(classes = "code-window floating") {
                        div(classes = "code-window-header") {
                            div(classes = "window-dot") { }
                            div(classes = "window-dot") { }
                            div(classes = "window-dot") { }
                            div(classes = "code-window-title") { +"main.kt" }
                        }
                        div(classes = "code-content") {
                            div(classes = "code-line") {
                                span(classes = "code-keyword") { +"fun" }
                                +" "
                                span(classes = "code-function") { +"main" }
                                +"() {"
                            }
                            div(classes = "code-line") {
                                +"  "
                                span(classes = "code-keyword") { +"val" }
                                +" developer = "
                                span(classes = "code-function") { +"Developer" }
                                +"("
                            }
                            div(classes = "code-line") {
                                +"    name = "
                                span(classes = "code-string") { +"\"Yousef\"" }
                                +","
                            }
                            div(classes = "code-line") {
                                +"    skills = "
                                span(classes = "code-function") { +"listOf" }
                                +"("
                                span(classes = "code-string") { +"\"Kotlin Multiplatform\"" }
                                +", "
                                span(classes = "code-string") { +"\"Quarkus/Spring Boot\"" }
                                +", "
                                span(classes = "code-string") { +"\"VueJS\"" }
                                +")"
                            }
                            div(classes = "code-line") { +"  )" }
                            div(classes = "code-line") { +"  " }
                            div(classes = "code-line") {
                                +"  developer."
                                span(classes = "code-function") { +"createAmazingSoftware" }
                                +"()"
                            }
                            div(classes = "code-line") { +"}" }
                        }
                    }

                    // Terminal
                    div(classes = "terminal pulse") {
                        div(classes = "terminal-header") {
                            div(classes = "terminal-title") { +"~/projects $ " }
                        }
                        div(classes = "terminal-content") {
                            div {
                                span(classes = "prompt") { +"$" }
                                span(classes = "command") { +"run portfolio.sh" }
                            }
                            div(classes = "output") {
                                +"Initializing project environment...\n"
                                +"Compiling Kotlin code...\n"
                                +"Setting up API endpoints...\n"
                                +"Loading 3D models...\n"
                                +"\n"
                                +"Portfolio deployed successfully at port 8080!"
                            }
                            div {
                                span(classes = "prompt") { +"$" }
                                span(classes = "command") { +"_" }
                            }
                        }
                    }
                }
            }

            // Projects Section
            section(classes = "section") {
                id = "projects"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Featured Projects" }
                    p(classes = "section-desc") {
                        +"Explore my latest work showcasing innovative solutions and cutting-edge technologies."
                    }
                }

                div(classes = "projects-grid") {
                    // Generate projects based on the entities
                    projectEntities.forEachIndexed { index, project ->
                        div(classes = "project-card") {
                            div(classes = "project-image") {
                                // Use different colors based on index
                                val colors = listOf("00f7ff", "ff2a6d", "05ffa1", "b967ff", "0162ff")
                                val color = colors[index % colors.size]
                                img(
                                    src = "https://placehold.co/600x400/020024/$color?text=${project.title}",
                                    alt = project.title
                                ) { }
                            }
                            div(classes = "project-content") {
                                h3(classes = "project-title") { +project.title }
                                p(classes = "project-desc") { +project.description }
                                div(classes = "project-tags") {
                                    project.technologies.forEach { tech ->
                                        span(classes = "project-tag") { +tech }
                                    }
                                }
                                div(classes = "project-links") {
                                    a(href = "#", classes = "project-link") { +"Live Demo" }
                                    a(href = "#", classes = "project-link") { +"Github" }
                                }
                            }
                        }
                    }

                    // If no projects, add placeholders
                    if (projectEntities.isEmpty()) {
                        // Add 6 placeholder projects
                        for (i in 1..6) {
                            div(classes = "project-card") {
                                div(classes = "project-image") {
                                    val colors = listOf("00f7ff", "ff2a6d", "05ffa1", "b967ff", "0162ff", "00f7ff")
                                    img(
                                        src = "https://placehold.co/600x400/020024/${colors[i - 1]}?text=Project+$i",
                                        alt = "Project $i"
                                    ) { }
                                }
                                div(classes = "project-content") {
                                    h3(classes = "project-title") { +"Project $i" }
                                    p(classes = "project-desc") { +"Description for project $i. This is a sample project showcasing various technologies." }
                                    div(classes = "project-tags") {
                                        span(classes = "project-tag") { +"Kotlin" }
                                        span(classes = "project-tag") { +"Quarkus" }
                                        span(classes = "project-tag") { +"React" }
                                    }
                                    div(classes = "project-links") {
                                        a(href = "#", classes = "project-link") { +"Live Demo" }
                                        a(href = "#", classes = "project-link") { +"Github" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Skills Section
            section(classes = "section") {
                id = "skills"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Technical Skills" }
                    p(classes = "section-desc") { +"Advanced technologies powering my development stack" }
                }

                div(classes = "tech-circuit") {
                    // Circuit lines and nodes for visual effect
                    div(classes = "circuit-lines") {
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}
                        div(classes = "circuit-line") {}

                        // Nodes
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
                        // Kotlin Compose Multiplatform
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    img(
                                        src = "https://developer.android.com/static/images/jetpack/compose-tutorial/compose-logo.svg",
                                        alt = "Compose Multiplatform",
                                        classes = "skill-logo"
                                    ) {}
                                    h3(classes = "skill-title") { +"Compose" }
                                    p(classes = "skill-subtitle") { +"Multiplatform UI Framework" }
                                }
                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"Compose Multiplatform" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"Declarative UI approach" }
                                        li(classes = "skill-detail-item") { +"Share UI across platforms" }
                                        li(classes = "skill-detail-item") { +"Android, iOS, and desktop" }
                                        li(classes = "skill-detail-item") { +"Modern UI development pattern" }
                                    }
                                }
                            }
                        }

                        // Quarkus/Spring Boot
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    // Combined logo display
                                    div(classes = "") {
                                        attributes["style"] =
                                            "display: flex; justify-content: center; align-items: center; width: 100px; height: 100px; margin-bottom: 20px;"
                                        img(
                                            src = "https://design.jboss.org/quarkus/logo/final/SVG/quarkus_logo_horizontal_rgb_default.svg",
                                            alt = "Quarkus",
                                            classes = "skill-logo"
                                        ) {
                                            attributes["style"] =
                                                "width: 80px; height: auto; margin-right: -30px;"
                                        }
                                        img(
                                            src = "https://spring.io/img/spring.svg",
                                            alt = "Spring Boot",
                                            classes = "skill-logo"
                                        ) {
                                            attributes["style"] =
                                                "width: 60px; height: auto; margin-left: -30px;"
                                        }
                                    }
                                    h3(classes = "skill-title") { +"Quarkus/Spring" }
                                    p(classes = "skill-subtitle") { +"Backend Frameworks" }
                                }
                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"Quarkus/Spring Boot" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"Fast startup & low memory" }
                                        li(classes = "skill-detail-item") { +"Microservices architecture" }
                                        li(classes = "skill-detail-item") { +"Reactive programming" }
                                        li(classes = "skill-detail-item") { +"Cloud-native development" }
                                    }
                                }
                            }
                        }
                        // Android/iOS
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    // Combined logo display
                                    div(classes = "") {
                                        attributes["style"] =
                                            "display: flex; justify-content: center; align-items: center; width: 100px; height: 100px; margin-bottom: 20px;"
                                        img(
                                            src = "https://developer.android.com/static/images/brand/Android_Robot.svg",
                                            alt = "Android",
                                            classes = "skill-logo"
                                        ) {
                                            attributes["style"] =
                                                "width: 70px; height: auto; margin-right: -20px;"
                                        }
                                        img(
                                            src = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/IOS_logo.svg/512px-IOS_logo.svg.png",
                                            alt = "iOS",
                                            classes = "skill-logo"
                                        ) {
                                            attributes["style"] =
                                                "width: 70px; height: auto; margin-left: -20px;"
                                        }
                                    }
                                    h3(classes = "skill-title") { +"Android/iOS" }
                                    p(classes = "skill-subtitle") { +"Mobile Development" }
                                }
                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"Android/iOS" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"Native app development" }
                                        li(classes = "skill-detail-item") { +"Cross-platform solutions" }
                                        li(classes = "skill-detail-item") { +"Material Design & SwiftUI" }
                                        li(classes = "skill-detail-item") { +"App store optimization" }
                                    }
                                }
                            }
                        }

                        // Vue/Nuxt
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    img(
                                        src = "https://vuejs.org/images/logo.png",
                                        alt = "Vue.js",
                                        classes = "skill-logo"
                                    ) {}
                                    h3(classes = "skill-title") { +"Vue/Nuxt" }
                                    p(classes = "skill-subtitle") { +"Frontend Frameworks" }
                                }
                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"Vue/Nuxt" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"Component-based architecture" }
                                        li(classes = "skill-detail-item") { +"Server-side rendering" }
                                        li(classes = "skill-detail-item") { +"State management with Pinia" }
                                        li(classes = "skill-detail-item") { +"Vue 3 Composition API" }
                                    }
                                }
                            }
                        }

                        // PostgreSQL
                        div(classes = "skill-card") {
                            div(classes = "skill-card-inner") {
                                div(classes = "skill-card-front") {
                                    div(classes = "skill-card-bg") {
                                        div(classes = "skill-card-bg-circle") {}
                                        div(classes = "skill-card-bg-circle") {}
                                    }
                                    img(
                                        src = "https://www.postgresql.org/media/img/about/press/elephant.png",
                                        alt = "PostgreSQL",
                                        classes = "skill-logo"
                                    ) {}
                                    h3(classes = "skill-title") { +"PostgreSQL" }
                                    p(classes = "skill-subtitle") { +"Advanced Database" }
                                }
                                div(classes = "skill-card-back") {
                                    h3(classes = "skill-title") { +"PostgreSQL" }
                                    ul(classes = "skill-detail-list") {
                                        li(classes = "skill-detail-item") { +"ACID compliant transactions" }
                                        li(classes = "skill-detail-item") { +"JSON & NoSQL capabilities" }
                                        li(classes = "skill-detail-item") { +"Advanced indexing strategies" }
                                        li(classes = "skill-detail-item") { +"High performance scaling" }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Contact Section
            section(classes = "section") {
                id = "contact"
                div(classes = "section-header") {
                    h2(classes = "section-title") { +"Get In Touch" }
                    p(classes = "section-desc") {
                        +"Have a project in mind? Let's discuss how we can work together to bring your ideas to life."
                    }
                }

                div(classes = "contact-container") {
                    div(classes = "contact-info") {
                        h3 { +"Contact Information" }
                        p { +"Feel free to reach out through any of these channels. I'm always open to discussing new projects and opportunities." }

                        div(classes = "contact-item") {
                            div(classes = "contact-icon") {
                                unsafe {
                                    +"""<svg viewBox="0 0 24 24">
                                      <path d="M20,4H4C2.9,4,2,4.9,2,6v12c0,1.1,0.9,2,2,2h16c1.1,0,2-0.9,2-2V6C22,4.9,21.1,4,20,4z M20,8l-8,5L4,8V6l8,5l8-5V8z"/>
                                    </svg>"""
                                }
                            }
                            div(classes = "contact-text") {
                                a(href = "mailto:contact@example.com") { +"contact@example.com" }
                            }
                        }

                        div(classes = "contact-item") {
                            div(classes = "contact-icon") {
                                unsafe {
                                    +"""<svg viewBox="0 0 24 24">
                                      <path d="M12,2C8.13,2,5,5.13,5,9c0,5.25,7,13,7,13s7-7.75,7-13C19,5.13,15.87,2,12,2z M12,11.5c-1.38,0-2.5-1.12-2.5-2.5 s1.12-2.5,2.5-2.5s2.5,1.12,2.5,2.5S13.38,11.5,12,11.5z"/>
                                    </svg>"""
                                }
                            }
                            div(classes = "contact-text") { +"New York, NY, USA" }
                        }

                        div(classes = "contact-item") {
                            div(classes = "contact-icon") {
                                unsafe {
                                    +"""<svg viewBox="0 0 24 24">
                                      <path d="M12,2A10,10 0 0,0 2,12C2,16.42 4.87,20.17 8.84,21.5C9.34,21.58 9.5,21.27 9.5,21C9.5,20.77 9.5,20.14 9.5,19.31C6.73,19.91 6.14,17.97 6.14,17.97C5.68,16.81 5.03,16.5 5.03,16.5C4.12,15.88 5.1,15.9 5.1,15.9C6.1,15.97 6.63,16.93 6.63,16.93C7.5,18.45 8.97,18 9.54,17.76C9.63,17.11 9.89,16.67 10.17,16.42C7.95,16.17 5.62,15.31 5.62,11.5C5.62,10.39 6,9.5 6.65,8.79C6.55,8.54 6.2,7.5 6.75,6.15C6.75,6.15 7.59,5.88 9.5,7.17C10.29,6.95 11.15,6.84 12,6.84C12.85,6.84 13.71,6.95 14.5,7.17C16.41,5.88 17.25,6.15 17.25,6.15C17.8,7.5 17.45,8.54 17.35,8.79C18,9.5 18.38,10.39 18.38,11.5C18.38,15.32 16.04,16.16 13.81,16.41C14.17,16.72 14.5,17.33 14.5,18.26C14.5,19.6 14.5,20.68 14.5,21C14.5,21.27 14.66,21.59 15.17,21.5C19.14,20.16 22,16.42 22,12A10,10 0 0,0 12,2Z"/>
                                    </svg>"""
                                }
                            }
                            div(classes = "contact-text") {
                                a(
                                    href = "https://github.com/yourusername",
                                    target = "_blank"
                                ) { +"github.com/yourusername" }
                            }
                        }
                    }

                    div(classes = "contact-form") {
                        form {
                            id = "contact-form"
                            div(classes = "form-group") {
                                label(
                                    classes = "form-label"
                                ) {
                                    attributes["for"] = "name"
                                    +"Name"
                                }
                                input(
                                    type = InputType.text,
                                    classes = "form-input",
                                    name = "name"
                                ) {
                                    id = "name"
                                    required =
                                        true
                                }
                            }

                            div(classes = "form-group") {
                                label(
                                    classes = "form-label"
                                ) {
                                    attributes["for"] = "email"
                                    +"Email"
                                }
                                input(
                                    type = InputType.email,
                                    classes = "form-input",
                                    name = "email"
                                ) {
                                    id = "email"
                                    required =
                                        true
                                }
                            }

                            div(classes = "form-group") {
                                label(
                                    classes = "form-label"
                                ) {
                                    attributes["for"] = "subject"
                                    +"Subject"
                                }
                                input(
                                    type = InputType.text,
                                    classes = "form-input",
                                    name = "subject"
                                ) {
                                    id = "subject"
                                    required =
                                        true
                                }
                            }

                            div(classes = "form-group") {
                                label(
                                    classes = "form-label"
                                ) {
                                    attributes["for"] = "message"
                                    +"Message"
                                }
                                textArea(
                                    classes = "form-textarea"
                                ) {
                                    attributes["name"] = "message"
                                    attributes["id"] = "message"
                                    required = true
                                }
                            }

                            button(
                                type = ButtonType.submit,
                                classes = "cta-button"
                            ) { +"Send Message" }
                        }
                    }
                }
            }
}
                return writer.toString()
            }

            /**
             * Builds the projects section using Kotlin HTML DSL
             */
            fun buildProjectsSection(
                projectEntities: List<ProjectEntity>
            ): String {
                val writer =
                    StringWriter()
                writer.appendHTML()
                    .div {
                        attributes["class"] =
                            "projects-grid"

                        projectEntities.forEachIndexed { index, project ->
                            div(classes = "project-card") {
                                // Project image
                                div(classes = "project-image") {
                                    // Use different colors based on index
                                    val colors =
                                        listOf(
                                            "00f7ff",
                                            "ff2a6d",
                                            "05ffa1",
                                            "b967ff",
                                            "0162ff"
                                        )
                                    val color =
                                        colors[index % colors.size]
                                    img(
                                        src = "https://placehold.co/600x400/020024/$color?text=${project.title}",
                                        alt = project.title
                                    ) { }
                                }

                                // Project content
                                div(classes = "project-content") {
                                    h3(classes = "project-title") { +project.title }
                                    p(classes = "project-desc") { +project.description }
                                    div(classes = "project-tags") {
                                        project.technologies.forEach { tech ->
                                            span(
                                                classes = "project-tag"
                                            ) { +tech }
                                        }
                                    }
                                    div(classes = "project-links") {
                                        a(
                                            href = project.demoUrl,
                                            target = "_blank",
                                            classes = "project-link"
                                        ) { +"Live Demo" }
                                        a(
                                            href = project.githubUrl,
                                            target = "_blank",
                                            classes = "project-link"
                                        ) { +"Github" }
                                    }
                                }
                            }
                        }
                    }
                return writer.toString()
            }
        }