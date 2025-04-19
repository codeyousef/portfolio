package code.yousef.infrastructure.qute

import code.yousef.summon.integrations.quarkus.qute.QuteTemplate
import code.yousef.summon.integrations.quarkus.qute.QuteTemplateRenderer
import code.yousef.summon.integrations.quarkus.htmx.htmlAttribute
import code.yousef.summon.modifier.Modifier
import code.yousef.summon.runtime.Composable
import io.quarkus.arc.Unremovable
import io.quarkus.qute.Template
import io.quarkus.qute.Location
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

/**
 * Components that use Qute templates to render UI components.
 * This class uses the library's QuteTemplateRenderer to render Qute templates.
 */
@ApplicationScoped
@Unremovable
class QuteComponents {

    @Location("home-page-component.html")
    @Inject
    lateinit var homePageTemplate: Template

    @Location("projects-section-component.html")
    @Inject
    lateinit var projectsSectionTemplate: Template

    @Location("skills-section-component.html")
    @Inject
    lateinit var skillsSectionTemplate: Template

    @Location("contact-section-component.html")
    @Inject
    lateinit var contactSectionTemplate: Template

    /**
     * Renders the home page component using a Qute template.
     * 
     * @return The rendered HTML
     */
    fun renderHomePageComponent(): String {
        return QuteTemplateRenderer.renderTemplate(homePageTemplate)
    }

    /**
     * Renders the projects section component using a Qute template.
     * 
     * @param projects The list of projects to display
     * @return The rendered HTML
     */
    fun renderProjectsSectionComponent(projects: List<Any>): String {
        return QuteTemplateRenderer.renderTemplate(projectsSectionTemplate, "projects" to projects)
    }

    /**
     * Renders the skills section component using a Qute template.
     * 
     * @return The rendered HTML
     */
    fun renderSkillsSectionComponent(): String {
        return QuteTemplateRenderer.renderTemplate(skillsSectionTemplate)
    }

    /**
     * Renders the contact section component using a Qute template.
     * 
     * @return The rendered HTML
     */
    fun renderContactSectionComponent(): String {
        return QuteTemplateRenderer.renderTemplate(contactSectionTemplate)
    }
}

/**
 * A composable function that renders the home page component using Qute.
 * This function is used in Summon components to render the home page component.
 * 
 * @param quteComponents The QuteComponents instance to use for rendering
 */
@Composable
fun HomePageComponent(quteComponents: QuteComponents) {
    println("HomePageComponent rendering")

    // Use the library's QuteTemplate component to render the home page component
    QuteTemplate(
        template = quteComponents.homePageTemplate,
        modifier = Modifier()
            .htmlAttribute("id", "home-page-component")
            .htmlAttribute("data-component", "home-page")
    )

    println("HomePageComponent rendering completed")
}

/**
 * A composable function that renders the projects section component using Qute.
 * This function is used in Summon components to render the projects section component.
 * 
 * @param projects The list of projects to display
 * @param quteComponents The QuteComponents instance to use for rendering
 */
@Composable
fun ProjectsSectionComponent(projects: List<Any>, quteComponents: QuteComponents) {
    println("ProjectsSectionComponent rendering with ${projects.size} projects")

    // Use the library's QuteTemplate component to render the projects section component
    QuteTemplate(
        template = quteComponents.projectsSectionTemplate,
        modifier = Modifier()
            .htmlAttribute("id", "projects-section-component")
            .htmlAttribute("data-component", "projects-section"),
        "projects" to projects
    )

    println("ProjectsSectionComponent rendering completed")
}

/**
 * A composable function that renders the skills section component using Qute.
 * This function is used in Summon components to render the skills section component.
 * 
 * @param quteComponents The QuteComponents instance to use for rendering
 */
@Composable
fun SkillsSectionComponent(quteComponents: QuteComponents) {
    println("SkillsSectionComponent rendering")

    // Use the library's QuteTemplate component to render the skills section component
    QuteTemplate(
        template = quteComponents.skillsSectionTemplate,
        modifier = Modifier()
            .htmlAttribute("id", "skills-section-component")
            .htmlAttribute("data-component", "skills-section")
    )

    println("SkillsSectionComponent rendering completed")
}

/**
 * A composable function that renders the contact section component using Qute.
 * This function is used in Summon components to render the contact section component.
 * 
 * @param quteComponents The QuteComponents instance to use for rendering
 */
@Composable
fun ContactSectionComponent(quteComponents: QuteComponents) {
    println("ContactSectionComponent rendering")

    // Use the library's QuteTemplate component to render the contact section component
    QuteTemplate(
        template = quteComponents.contactSectionTemplate,
        modifier = Modifier()
            .htmlAttribute("id", "contact-section-component")
            .htmlAttribute("data-component", "contact-section")
    )

    println("ContactSectionComponent rendering completed")
}
