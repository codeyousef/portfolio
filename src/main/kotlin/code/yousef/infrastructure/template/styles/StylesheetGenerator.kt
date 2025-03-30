package code.yousef.infrastructure.template.styles

import kotlinx.css.*
import jakarta.enterprise.context.ApplicationScoped
import org.jboss.logging.Logger

@ApplicationScoped
class StylesheetGenerator {
    private val logger = Logger.getLogger(StylesheetGenerator::class.java)
    private val styleGenerators = listOf(
        BaseStyles(),
        LayoutStyles(),
        ComponentStyles(),
        ProjectStyles(),
        TerminalStyles(),
        AnimationStyles(),
        ThemeStyles(),
        MediaQueryStyles()
    )

    fun generateStyles(): String {
        val cssBuilder = CssBuilder()
        
        try {
            // Apply all style generators
            styleGenerators.forEach { generator ->
                generator.generateStyles(cssBuilder)
            }
            
            // Return the generated CSS
            val css = cssBuilder.toString()
            
            // Log the first 500 characters for debugging
            logger.debug("Generated CSS (first 500 chars): ${css.take(500)}...")
            
            return css
        } catch (e: Exception) {
            logger.error("Error generating CSS", e)
            // Return fallback CSS in case of error
            return fallbackCSS()
        }
    }
    
    /**
     * Provides a basic fallback CSS in case the Kotlin CSS DSL has issues
     */
    private fun fallbackCSS(): String {
        return """
            /* Fallback CSS if Kotlin CSS DSL fails */
            :root {
              --primary: #00f7ff;
              --secondary: #ff2a6d;
              --bg-dark: #0a0a14;
            }
            
            body {
              font-family: 'Space Grotesk', sans-serif;
              background-color: var(--bg-dark);
              color: white;
              margin: 0;
              padding: 0;
            }
            
            .project-card {
              border: 1px solid var(--primary);
              border-radius: 8px;
              overflow: hidden;
              margin-bottom: 20px;
            }
            
            .projects-grid {
              display: grid;
              grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
              gap: 20px;
              padding: 20px;
            }
        """.trimIndent()
    }
}