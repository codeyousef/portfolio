package code.yousef.infrastructure.summon

import code.yousef.summon.components.display.Text
import code.yousef.summon.components.layout.Box
import code.yousef.summon.integrations.quarkus.EnhancedQuarkusExtension
import code.yousef.summon.modifier.Modifier
import code.yousef.summon.runtime.Composable
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.createHTML

/**
 * A renderer for HTML components integrated with Quarkus.
 * This class handles the transformation of components into HTML.
 * It uses the EnhancedSummonRenderer from the Summon library's Quarkus integration.
 */
@ApplicationScoped
class SummonRenderer {

    @Inject
    private lateinit var summonRenderer: EnhancedQuarkusExtension.EnhancedSummonRenderer

    /**
     * Helper method to render the composable content using the EnhancedSummonRenderer.
     * 
     * @param content The composable content lambda
     * @return The rendered HTML as a string
     */
    private fun renderWithEnhancedRenderer(content: @Composable () -> Unit): String {
        println("SummonRenderer.renderWithEnhancedRenderer() called")

        try {
            // Use the EnhancedSummonRenderer to render the composable content
            println("About to call summonRenderer.renderToString()")
            val result = summonRenderer.renderToString(content)
            println("summonRenderer.renderToString() completed successfully, result length: ${result.length}")
            return result
        } catch (e: Exception) {
            println("ERROR in renderWithEnhancedRenderer: ${e.message}")
            println("Exception type: ${e.javaClass.name}")
            println("Stack trace: ${e.stackTraceToString()}")
            throw e  // Re-throw the exception to be handled by the caller
        }
    }

    /**
     * Render HTML content with a full page structure using Summon composables.
     *
     * @param title The page title
     * @param description Meta description for the page
     * @param content The composable content lambda
     * @return The rendered HTML as a string
     */
    fun render(
        title: String = "Portfolio - Yousef",
        description: String = "Professional portfolio showcasing projects, services, and blog",
        content: @Composable () -> Unit
    ): String {
        println("SummonRenderer.render() called with title: $title")

        try {
            // Use the Summon library's EnhancedQuarkusExtension.EnhancedSummonRenderer to render the content
            println("SummonRenderer.render() - Rendering composable content using Summon's EnhancedQuarkusExtension...")

            // Render the composable content to a string
            val renderedComposableHtml = try {
                println("Calling renderWithEnhancedRenderer to render composable content")
                val html = renderWithEnhancedRenderer(content)
                println("Composable content rendered successfully, length: ${html.length}")
                println("First 100 chars of rendered HTML: ${html.take(100)}")
                html
            } catch (e: Exception) {
                println("ERROR rendering composable content: ${e.message}")
                println("Exception type: ${e.javaClass.name}")
                println("Stack trace: ${e.stackTraceToString()}")
                // Provide error details within the page structure
                println("Attempting to render error component")
                renderWithEnhancedRenderer { ErrorComponent("Composable Rendering Error", e) }
            }

            // Use the Summon library's EnhancedQuarkusExtension.EnhancedSummonRenderer to render the template
            println("Calling summonRenderer.renderTemplate with title: $title and rendered HTML")
            println("renderedComposableHtml length: ${renderedComposableHtml.length}")

            try {
                val result = summonRenderer.renderTemplate(title, renderedComposableHtml)
                println("Template rendered successfully, result length: ${result.length}")
                println("First 100 chars of final HTML: ${result.take(100)}")

                println("SummonRenderer.render() completed successfully, result length: ${result.length}")
                return result
            } catch (e: Exception) {
                println("ERROR in summonRenderer.renderTemplate: ${e.message}")
                println("Exception type: ${e.javaClass.name}")
                println("Stack trace: ${e.stackTraceToString()}")
                throw e  // Re-throw to be caught by the outer try-catch
            }
        } catch (e: Exception) {
            // Catch errors during overall page structure rendering
            println("ERROR in SummonRenderer.render() structure generation: ${e.message}")
            println("Exception type: ${e.javaClass.name}")
            println("Stack trace: ${e.stackTraceToString()}")
            return createErrorHtml("Page Structure Rendering Error", e)
        }
    }

    /**
     * Render an HTML fragment using Summon composables.
     * This is useful for HTMX partial updates.
     *
     * @param content The composable content lambda
     * @return The rendered HTML fragment as a string
     */
    fun render(content: @Composable () -> Unit): String {
        println("SummonRenderer.render() called without title (component-only)")

        try {
            // Render the composable directly using EnhancedSummonRenderer
            val result = renderWithEnhancedRenderer(content)

            println("SummonRenderer.render() completed successfully for component, result length: ${result.length}")
            return result
        } catch (e: Exception) {
            println("ERROR in SummonRenderer.render() (component-only): ${e.message}")
            // Return an error message formatted as HTML
            return "<div style='color: red; padding: 1rem; border: 1px solid red;'>Rendering Error: ${e.message}<br><pre>${
                e.stackTraceToString().take(500)
            }...</pre></div>"
        }
    }

    private fun createErrorHtml(errorTitle: String, e: Exception): String {
        return createHTML().html {
            head {
                title("Rendering Error")
            }
            body {
                h1 { +"Rendering Error" }
                p { +"There was an error rendering the page:" }
                pre { +e.message!! }
                pre { +e.stackTraceToString() }
                p { a("/") { +"Return to Home" } }
            }
        }
    }

    @Composable
    private fun ErrorComponent(title: String, e: Exception) {
        // The EnhancedSummonRenderer handles the rendering context
        Box(modifier = Modifier().style("color", "red").style("padding", "1rem")) {
            Text("Error: $title")
            Text("Message: ${e.message ?: "No details"}")
        }
    }
}
