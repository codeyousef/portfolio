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
                                    img(src = "https://play-lh.googleusercontent.com/jf-wUvZHEVzkyRSiCQiv1IxGsQ4bi0FeKYpVAb5hqkgIIGJqRTGSQ87Q_hbuCJgTsIQ", alt = "Kotlin", classes = "skill-logo") {}
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
                                    img(src = "https://developer.android.com/static/images/jetpack/compose-tutorial/compose-logo.svg", alt = "Compose Multiplatform", classes = "skill-logo") {}
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
                                    div {
                                        attributes["style"] = "display: flex; justify-content: center; align-items: center; width: 100px; height: 100px; margin-bottom: 20px;"
                                        img(src = "https://design.jboss.org/quarkus/logo/final/SVG/quarkus_logo_horizontal_rgb_default.svg", alt = "Quarkus", classes = "skill-logo") {
                                            attributes["style"] = "width: 80px; height: auto; margin-right: -30px;"
                                        }
                                        img(src = "https://spring.io/img/spring.svg", alt = "Spring Boot", classes = "skill-logo") {
                                            attributes["style"] = "width: 60px; height: auto; margin-left: -30px;"
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
                                    div {
                                        attributes["style"] = "display: flex; justify-content: center; align-items: center; width: 100px; height: 100px; margin-bottom: 20px;"
                                        img(src = "https://developer.android.com/static/images/brand/Android_Robot.svg", alt = "Android", classes = "skill-logo") {
                                            attributes["style"] = "width: 70px; height: auto; margin-right: -20px;"
                                        }
                                        img(src = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/IOS_logo.svg/512px-IOS_logo.svg.png", alt = "iOS", classes = "skill-logo") {
                                            attributes["style"] = "width: 70px; height: auto; margin-left: -20px;"
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
                                    img(src = "https://vuejs.org/images/logo.png", alt = "Vue.js", classes = "skill-logo") {}
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
                                    img(src = "https://www.postgresql.org/media/img/about/press/elephant.png", alt = "PostgreSQL", classes = "skill-logo") {}
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
     */
    fun buildProjectsSection(projectEntities: List<ProjectEntity>): String {
        val writer = StringWriter()
        writer.appendHTML().div(classes = "projects-grid") {
            projectEntities.forEach { project ->
                div(classes = "project-card") {
                    // Project image
                    div(classes = "project-image") {
                        img(src = "https://placehold.co/600x400/020024/00f7ff?text=${project.title}", alt = project.title) {
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