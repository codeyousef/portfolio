package code.yousef.infrastructure.qute

import code.yousef.summon.runtime.Composable
import code.yousef.ui.AppRoot
import code.yousef.ui.HomePage
import io.quarkus.arc.Unremovable
import io.quarkus.qute.RawString
import io.quarkus.qute.TemplateExtension
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.spi.CDI
import jakarta.inject.Inject

/**
 * Template extension for rendering Summon components in Qute templates.
 * This class provides the summonComponent:render directive used in Qute templates.
 */
@ApplicationScoped
@Unremovable
class SummonComponentRegistry @Inject constructor(
    private val quteComponents: QuteComponents
) {
    // Map of component names to component rendering functions
    private val components = mapOf(
        "homePage" to { quteComponents.renderHomePageComponent() },
        "projectsSection" to { quteComponents.renderProjectsSectionComponent(emptyList()) },
        "skillsSection" to { quteComponents.renderSkillsSectionComponent() },
        "contactSection" to { quteComponents.renderContactSectionComponent() }
    )

    /**
     * Get a component by name.
     * 
     * @param name The name of the component to get
     * @return The component rendering function, or null if not found
     */
    fun getComponent(name: String): (() -> String)? {
        return components[name]
    }
}

/**
 * Template extension methods for rendering Summon components in Qute templates.
 * This class provides the summonComponent:render directive used in Qute templates.
 */
@ApplicationScoped
@Unremovable
class SummonComponentTemplateExtension @Inject constructor(
    private val componentRegistry: SummonComponentRegistry
) {
    // The class is kept for dependency injection purposes
}

/**
 * Render a Summon component by name.
 * This method is called when using the summonComponent:render directive in Qute templates.
 * 
 * @param name The name of the component to render
 * @return The rendered HTML as RawString to prevent escaping
 */
@TemplateExtension(namespace = "summonComponent")
fun render(name: String): RawString {
    println("SummonComponentTemplateExtension.render() called with name: $name")
    System.err.println("CRITICAL: SummonComponentTemplateExtension.render() called with name: $name")

    try {
        // Get the component registry from CDI
        val registry = CDI.current().select(SummonComponentRegistry::class.java).get()

        val component = registry.getComponent(name)
        if (component != null) {
            println("Component found, rendering...")
            System.err.println("CRITICAL: Component found, rendering...")

            try {
                val result = component()
                println("Component rendered successfully, result length: ${result.length}")
                System.err.println("CRITICAL: Component rendered successfully, result length: ${result.length}")
                return RawString(result)
            } catch (dbException: Exception) {
                // Check if it's a database connection issue
                if (dbException.message?.contains("Connection refused") == true || 
                    dbException.cause?.message?.contains("Connection refused") == true) {
                    System.err.println("CRITICAL: Database connection failed while rendering component: $name")

                    // Provide fallback content for specific components
                    val fallbackContent = when (name) {
                        "projectsSection" -> {
                            System.err.println("CRITICAL: Using fallback for projects section")
                            """
                            <section id="projects" class="py-16 bg-gray-50">
                                <div class="container mx-auto px-4">
                                    <h2 class="text-3xl font-bold text-center mb-8">Projects</h2>
                                    <div class="text-center p-8 bg-white rounded-lg shadow-md">
                                        <p class="text-lg text-gray-600 mb-4">
                                            Projects are currently unavailable. The database connection could not be established.
                                        </p>
                                        <p class="text-sm text-gray-500">
                                            Please try again later or contact the administrator.
                                        </p>
                                    </div>
                                </div>
                            </section>
                            """.trimIndent()
                        }
                        else -> {
                            // For other components, try to render with empty data
                            try {
                                val quteComponents = CDI.current().select(QuteComponents::class.java).get()
                                when (name) {
                                    "homePage" -> quteComponents.renderHomePageComponent()
                                    "skillsSection" -> quteComponents.renderSkillsSectionComponent()
                                    "contactSection" -> quteComponents.renderContactSectionComponent()
                                    else -> "<!-- Component $name unavailable due to database connection issue -->"
                                }
                            } catch (e: Exception) {
                                "<!-- Error rendering fallback for component $name: ${e.message} -->"
                            }
                        }
                    }

                    return RawString(fallbackContent)
                } else {
                    // Re-throw if it's not a connection issue
                    throw dbException
                }
            }
        } else {
            println("Component not found: $name")
            System.err.println("CRITICAL: Component not found: $name")
            return RawString("<!-- Component not found: $name -->")
        }
    } catch (e: Exception) {
        println("Error rendering component: ${e.message}")
        System.err.println("CRITICAL ERROR rendering component: ${e.message}")
        println("Exception type: ${e.javaClass.name}")
        System.err.println("CRITICAL: Exception type: ${e.javaClass.name}")
        println("Stack trace: ${e.stackTraceToString()}")
        System.err.println("CRITICAL: Stack trace: ${e.stackTraceToString()}")
        return RawString("<!-- Error rendering component: ${e.message} -->")
    }
}
