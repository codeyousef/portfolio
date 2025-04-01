package code.yousef.infrastructure.template

// Assuming ProjectEntity is defined in this package or imported correctly
import code.yousef.infrastructure.persistence.entity.ProjectEntity
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import io.quarkus.qute.RawString // Import RawString
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import java.io.StringWriter

// --- Data Classes (Needed for structuring placeholder data & skill card generation) ---
private data class SkillData(
    val name: String,
    val subtitle: String,
    val logoUrl: String?,
    val details: List<String>
)

private data class ContactInfo(
    val email: String?,
    val location: String?,
    val phone: String?,
    val githubUsername: String?
)


// --- PortfolioTemplates Class (Structure matches user-provided code) ---

@ApplicationScoped
class PortfolioTemplates {

    @Location("base-layout.html") // Assuming this path is correct
    @Inject
    lateinit var baseTemplate: Template

    /**
     * Builds the home page using Kotlin HTML DSL and wraps it in the base Qute template.
     * Signature and implementation match the user-provided code.
     * Calls buildHomeContent ONLY.
     */
    fun buildHomePage(
        projectEntities: List<ProjectEntity> // Original signature
    ): TemplateInstance {
        // Generate the HTML content using Kotlin DSL (Now includes Hero, Projects, Skills, Contact)
        val contentHtml = buildHomeContent(projectEntities) // Calls the method below

        // Use Qute template for the base layout
        return baseTemplate
            .data("title", "Yousef's Portfolio - NeoTech") // Example title
            .data("content", RawString(contentHtml)) // Wrap in RawString
    }

    /**
     * Builds the main page content (Hero, Projects, Skills, Contact) using Kotlin HTML DSL.
     * Contains the updated HTML generation logic matching the target design.
     * Uses projectEntities for Projects section.
     * Uses static placeholder data for Skills and Contact sections.
     * Signature matches the user-provided code.
     */
    private fun buildHomeContent(
        projectEntities: List<ProjectEntity> // Original signature
    ): String {
        val writer = StringWriter()

        // --- Static Placeholder Data (defined inside the method) ---
        val skillsPlaceholder: List<SkillData> = listOf(
            SkillData("Kotlin", "Modern JVM Language", "https://kotlinlang.org/docs/images/kotlin-logo.png", listOf("Concise, safe, and expressive", "Coroutines for asynchronous code", "100% interoperable with Java", "Backed by JetBrains & Google")),
            SkillData("Compose", "Multiplatform UI Framework", "https://developer.android.com/static/images/jetpack/compose-tutorial/compose-logo.svg", listOf("Declarative UI approach", "Share UI across platforms", "Android, iOS, and desktop", "Modern UI development pattern")),
            SkillData("Quarkus/Spring", "Backend Frameworks", null, listOf("Fast startup & low memory", "Microservices architecture", "Reactive programming", "Cloud-native development")),
            SkillData("Android/iOS", "Mobile Development", null, listOf("Native app development", "Cross-platform solutions", "Material Design & SwiftUI", "App store optimization")),
            SkillData("Vue/Nuxt", "Frontend Frameworks", "https://vuejs.org/images/logo.png", listOf("Component-based architecture", "Server-side rendering", "State management with Pinia", "Vue 3 Composition API")),
            SkillData("PostgreSQL", "Advanced Database", "https://www.postgresql.org/media/img/about/press/elephant.png", listOf("ACID compliant transactions", "JSON & NoSQL capabilities", "Advanced indexing strategies", "High performance scaling"))
        )
        val contactInfoPlaceholder = ContactInfo(
            email = "contact@example.com",
            location = "New York, NY, USA",
            phone = "+1 (555) 123-4567",
            githubUsername = "yourusername"
        )
        // --- End Static Placeholder Data ---


        // Use appendHTML().div { ... } structure
        writer.appendHTML().div {
            // --- Hero Section ---
            section(classes = "hero") {
                id = "hero"
                div(classes = "hero-content fade-in") {
                    h1 { +"Crafting Digital Experiences With Cutting-Edge Tech" }
                    p { +"Software developer specializing in Kotlin, Quarkus, and modern web technologies. Let's build the future together." }
                    div {
                        a(href = "#projects", classes = "cta-button") { +"View Projects" }
                        a(href = "#contact", classes = "cta-button secondary") { +"Get In Touch" }
                    }
                }
                div(classes = "hero-glow") { }
                div(classes = "hero-glow-2") { }
                div(classes = "floating-elements") {
                    div(classes = "code-window floating") {
                        div(classes = "code-window-header") { div(classes = "window-dot") {}; div(classes = "window-dot") {}; div(classes = "window-dot") {}; div(classes = "code-window-title") { +"main.kt" } }
                        div(classes = "code-content") {
                            div(classes = "code-line") { span(classes = "code-keyword") { +"fun" }; +" "; span(classes = "code-function") { +"main" }; +"() {" }
                            div(classes = "code-line") { +"  "; span(classes = "code-keyword") { +"val" }; +" developer = "; span(classes = "code-function") { +"Developer" }; +"(" }
                            div(classes = "code-line") { +"    name = "; span(classes = "code-string") { +"\"Yousef\"" }; +"," }
                            div(classes = "code-line") { +"    skills = "; span(classes = "code-function") { +"listOf" }; +"("; span(classes = "code-string") { +"\"Kotlin Multiplatform\"" }; +", "; span(classes = "code-string") { +"\"Quarkus/Spring Boot\"" }; +", "; span(classes = "code-string") { +"\"VueJS\"" }; +")" }
                            div(classes = "code-line") { +"  )" }
                            div(classes = "code-line") { +"  " }
                            div(classes = "code-line") { +"  developer."; span(classes = "code-function") { +"createAmazingSoftware" }; +"()" }
                            div(classes = "code-line") { +"}" }
                        }
                    }
                    div(classes = "terminal pulse") {
                        div(classes = "terminal-header") { div(classes = "terminal-title") { +"~/projects $ " } }
                        div(classes = "terminal-content") {
                            div { span(classes = "prompt") { +"$" }; span(classes = "command") { +"run portfolio.sh" } }
                            div(classes = "output") { unsafe { +"Initializing project environment...<br>Compiling Kotlin code...<br>Setting up API endpoints...<br>Loading 3D models...<br><br>Portfolio deployed successfully at port 8080!" } }
                            div { span(classes = "prompt") { +"$" }; span(classes = "command") { +"_" } }
                        }
                    }
                }
            }

            // --- Projects Section (Moved here from buildProjectsSection) ---
            section(classes = "section") {
                id = "projects"
                div("container"){
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Featured Projects" }
                        p(classes = "section-desc") { +"Explore my latest work showcasing innovative solutions and cutting-edge technologies." }
                    }
                    div(classes = "projects-grid") {
                        if (projectEntities.isEmpty()) {
                            p { +"No projects available at the moment." }
                            // Optionally add placeholder generation here if desired when list is empty
                        } else {
                            projectEntities.forEach { project ->
                                projectCard(project) // Call helper
                            }
                        }
                    }
                }
            }


            // --- Skills Section (uses placeholder data) ---
            section(classes = "section") {
                id = "skills"
                div("container"){
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Technical Skills" }
                        p(classes = "section-desc") { +"Advanced technologies powering my development stack" }
                    }
                    div(classes = "tech-circuit") {
                        div(classes = "circuit-lines") {
                            div(classes = "circuit-line") {}; div(classes = "circuit-line") {}; div(classes = "circuit-line") {}; div(classes = "circuit-line") {}; div(classes = "circuit-line") {}
                        }
                        div(classes = "circuit-node") { attributes["style"] = "top: 35%; left: 20%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 50%; left: 50%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 65%; left: 80%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 25%; left: 60%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 75%; left: 40%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 55%; left: 10%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 15%; left: 35%;" }
                        div(classes = "circuit-node") { attributes["style"] = "top: 85%; left: 70%;" }

                        div(classes = "skill-cards-container") {
                            skillsPlaceholder.forEach { skill ->
                                skillCard(skill) // Call helper
                            }
                        }
                    }
                }
            }

            // --- Contact Section (uses placeholder data) ---
            section(classes = "section") {
                id = "contact"
                div("container"){
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Get In Touch" }
                        p(classes = "section-desc") { +"Have a project in mind? Let's discuss how we can work together to bring your ideas to life." }
                    }
                    div(classes = "contact-container") {
                        div(classes = "contact-info") {
                            h3 { +"Contact Information" }
                            p { +"Feel free to reach out through any of these channels. I'm always open to discussing new projects and opportunities." }
                            contactInfoPlaceholder.email?.let { email -> contactItem(iconSvg = "<svg viewBox=\"0 0 24 24\"><path d=\"M20,4H4C2.9,4,2,4.9,2,6v12c0,1.1,0.9,2,2,2h16c1.1,0,2-0.9,2-2V6C22,4.9,21.1,4,20,4z M20,8l-8,5L4,8V6l8,5l8-5V8z\"/></svg>", text = { a(href = "mailto:$email") { +email } }) }
                            contactInfoPlaceholder.location?.let { location -> contactItem(iconSvg = "<svg viewBox=\"0 0 24 24\"><path d=\"M12,2C8.13,2,5,5.13,5,9c0,5.25,7,13,7,13s7-7.75,7-13C19,5.13,15.87,2,12,2z M12,11.5c-1.38,0-2.5-1.12-2.5-2.5 s1.12-2.5,2.5-2.5s2.5,1.12,2.5,2.5S13.38,11.5,12,11.5z\"/></svg>", text = { +location }) }
                            contactInfoPlaceholder.phone?.let { phone -> contactItem(iconSvg = "<svg viewBox=\"0 0 24 24\"><path d=\"M20,15.5c-1.25,0-2.45-0.2-3.57-0.57c-0.35-0.11-0.74-0.03-1.02,0.24l-2.2,2.2c-2.83-1.44-5.15-3.75-6.59-6.59l2.2-2.21 c0.28-0.26,0.36-0.65,0.25-1C8.7,6.45,8.5,5.25,8.5,4c0-0.55-0.45-1-1-1H4C3.45,3,3,3.45,3,4c0,9.39,7.61,17,17,17 c0.55,0,1-0.45,1-1v-3.5C21,15.95,20.55,15.5,20,15.5z\"/></svg>", text = { +phone }) }
                            contactInfoPlaceholder.githubUsername?.let { username -> contactItem(iconSvg = "<svg viewBox=\"0 0 24 24\"><path d=\"M12,2A10,10 0 0,0 2,12C2,16.42 4.87,20.17 8.84,21.5C9.34,21.58 9.5,21.27 9.5,21C9.5,20.77 9.5,20.14 9.5,19.31C6.73,19.91 6.14,17.97 6.14,17.97C5.68,16.81 5.03,16.5 5.03,16.5C4.12,15.88 5.1,15.9 5.1,15.9C6.1,15.97 6.63,16.93 6.63,16.93C7.5,18.45 8.97,18 9.54,17.76C9.63,17.11 9.89,16.67 10.17,16.42C7.95,16.17 5.62,15.31 5.62,11.5C5.62,10.39 6,9.5 6.65,8.79C6.55,8.54 6.2,7.5 6.75,6.15C6.75,6.15 7.59,5.88 9.5,7.17C10.29,6.95 11.15,6.84 12,6.84C12.85,6.84 13.71,6.95 14.5,7.17C16.41,5.88 17.25,6.15 17.25,6.15C17.8,7.5 17.45,8.54 17.35,8.79C18,9.5 18.38,10.39 18.38,11.5C18.38,15.32 16.04,16.16 13.81,16.41C14.17,16.72 14.5,17.33 14.5,18.26C14.5,19.6 14.5,20.68 14.5,21C14.5,21.27 14.66,21.59 15.17,21.5C19.14,20.16 22,16.42 22,12A10,10 0 0,0 12,2Z\"/></svg>", text = { a(href = "https://github.com/$username", target = "_blank") { +"github.com/$username" } }) }
                        }
                        div(classes = "contact-form") {
                            form {
                                id = "contact-form"
                                div(classes = "form-group") { label(classes = "form-label") { attributes["for"] = "name"; +"Name" }; input(type = InputType.text, classes = "form-input", name = "name") { id = "name"; required = true } }
                                div(classes = "form-group") { label(classes = "form-label") { attributes["for"] = "email"; +"Email" }; input(type = InputType.email, classes = "form-input", name = "email") { id = "email"; required = true } }
                                div(classes = "form-group") { label(classes = "form-label") { attributes["for"] = "subject"; +"Subject" }; input(type = InputType.text, classes = "form-input", name = "subject") { id = "subject"; required = true } }
                                div(classes = "form-group") { label(classes = "form-label") { attributes["for"] = "message"; +"Message" }; textArea(classes = "form-textarea") { attributes["name"] = "message"; attributes["id"] = "message"; required = true } }
                                button(type = ButtonType.submit, classes = "cta-button") { +"Send Message" }
                            }
                        }
                    }
                }
            }
        }
        return writer.toString()
    }

    /**
     * Builds ONLY the projects section HTML using Kotlin HTML DSL.
     * Signature matches the user-provided code.
     * NOTE: This method is NOT called by buildHomePage in the user-provided structure.
     * Returning empty string to avoid generating unused duplicate content by default.
     * You can uncomment the generation logic if you call this method separately.
     */
    fun buildProjectsSection( // Kept signature from user code
        projectEntities: List<ProjectEntity>
    ): String {
        // Return empty string as this is not called by buildHomePage in the provided structure
        return ""
        /*
        // --- Uncomment below to generate projects HTML if calling this method separately ---
        val writer = StringWriter()
        writer.appendHTML().div {
             section(classes = "section") {
                id = "projects"
                 div("container"){
                    div(classes = "section-header") {
                        h2(classes = "section-title") { +"Featured Projects" }
                        p(classes = "section-desc") { +"Explore my latest work showcasing innovative solutions and cutting-edge technologies." }
                    }
                    div(classes = "projects-grid") {
                        if (projectEntities.isEmpty()) {
                            // Placeholder logic from user code (optional)
                            for (i in 1..6) {
                                div(classes = "project-card fade-in") {
                                    div(classes = "project-image") {
                                        val colors = listOf("00f7ff", "ff2a6d", "05ffa1", "b967ff", "0162ff", "00f7ff")
                                        img(src = "https://placehold.co/600x400/020024/${colors[i - 1]}?text=Project+$i", alt = "Project $i") {}
                                    }
                                    div(classes = "project-content") {
                                        h3(classes = "project-title") { +"Project $i" }
                                        p(classes = "project-desc") { +"Description for project $i. This is a sample project showcasing various technologies." }
                                        div(classes = "project-tags") { span(classes = "project-tag") { +"Kotlin" }; span(classes = "project-tag") { +"Quarkus" }; span(classes = "project-tag") { +"React" } }
                                        div(classes = "project-links") { a(href = "#", classes = "project-link") { +"Live Demo" }; a(href = "#", classes = "project-link") { +"Github" } }
                                    }
                                }
                            }
                        } else {
                            projectEntities.forEach { project ->
                                projectCard(project) // Call helper
                            }
                        }
                    }
                 }
            }
        }
        return writer.toString()
        */
    }


    // --- Helper Functions (Private within the class) ---

    /**
     * Generates the HTML for a single project card using target classes.
     * Adapted to use ProjectEntity.
     */
    private fun FlowContent.projectCard(project: ProjectEntity) {
        div("project-card fade-in") {
            div("project-image") {
                val imageUrl = project.imageUrl.takeIf { !it.isNullOrBlank() }
                    ?: "https://placehold.co/600x400/020024/00f7ff?text=${project.title.replace(" ", "+")}"
                img(src = imageUrl, alt = project.title) {
                    attributes["onerror"] = "this.onerror=null; this.src='https://placehold.co/600x400/0a0a14/ff2a6d?text=Image+Error';"
                }
            }
            div("project-content") {
                h3("project-title") { +project.title }
                p("project-desc") { +project.description }
                // Assuming ProjectEntity has 'technologies' list
                if (project.technologies.isNotEmpty()) {
                    div("project-tags") {
                        project.technologies.forEach { tag ->
                            span("project-tag") { +tag }
                        }
                    }
                }
                // Assuming ProjectEntity has 'demoUrl' and 'githubUrl'
                div("project-links") {
                    project.demoUrl?.takeIf { it.isNotBlank() }?.let { url ->
                        a(href = url, target = "_blank", classes = "project-link") { +"Live Demo" }
                    }
                    project.githubUrl?.takeIf { it.isNotBlank() }?.let { url ->
                        a(href = url, target = "_blank", classes = "project-link") { +"Github" }
                    }
                    if (project.demoUrl.isNullOrBlank() && project.githubUrl.isNullOrBlank()){
                        a(href = "#", classes = "project-link") { +"Details" } // Fallback
                    }
                }
            }
        }
    }

    /**
     * Generates the HTML for a single skill card (flipping effect) using target classes.
     */
    private fun FlowContent.skillCard(skill: SkillData) {
        div("skill-card fade-in") {
            div("skill-card-inner") {
                div("skill-card-front") {
                    div("skill-card-bg") { div("skill-card-bg-circle") {}; div("skill-card-bg-circle") {} }
                    skill.logoUrl?.takeIf { it.isNotBlank() }?.let { logo ->
                        img(src = logo, alt = skill.name, classes = "skill-logo") {
                            attributes["onerror"] = "this.onerror=null; this.parentElement.innerHTML = '<div class=\"skill-logo\" style=\"display:flex; align-items:center; justify-content:center; background:rgba(var(--primary-rgb),0.1); border-radius: 50%; color: var(--primary); font-size: 2rem; font-weight: bold; width: 80px; height: 80px; margin-bottom: 20px; filter: drop-shadow(0 0 10px rgba(var(--primary-rgb), 0.7));\">${skill.name.firstOrNull()?.uppercaseChar() ?: '?'}</div>';"
                        }
                    } ?: div("skill-logo") {
                        style="display:flex; align-items:center; justify-content:center; background:rgba(var(--primary-rgb),0.1); border-radius: 50%; color: var(--primary); font-size: 2rem; font-weight: bold; width: 80px; height: 80px; margin-bottom: 20px; filter: drop-shadow(0 0 10px rgba(var(--primary-rgb), 0.7));"
                        +(skill.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?")
                    }
                    h3("skill-title") { +skill.name }
                    p("skill-subtitle") { +skill.subtitle }
                }
                div("skill-card-back") {
                    h3("skill-title") { +skill.name }
                    if (skill.details.isNotEmpty()) {
                        ul("skill-detail-list") {
                            skill.details.forEach { detail -> li("skill-detail-item") { +detail } }
                        }
                    } else {
                        p("skill-subtitle") { +"More details coming soon."}
                    }
                }
            }
        }
    }

    /**
     * Helper function for generating contact items.
     */
    private fun FlowContent.contactItem(iconSvg: String, text: FlowContent.() -> Unit) {
        div("contact-item") {
            div("contact-icon") { unsafe { +iconSvg } }
            div("contact-text") { text() }
        }
    }
}
