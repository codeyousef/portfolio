package code.yousef.infrastructure.template.styles

import jakarta.enterprise.context.ApplicationScoped
import kotlinx.css.CssBuilder
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
        CodeWindowStyles(),
        AnimationStyles(),
        ThemeStyles(),
        MediaQueryStyles(),
        SkillStyles(),
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
              --primary-dark: #00c3c9;
              --primary-rgb: 0, 247, 255;
              --secondary: #ff2a6d;
              --secondary-dark: #d31557;
              --secondary-rgb: 255, 42, 109;
              --bg-dark: #0a0a14;
              --bg-card: rgba(16, 16, 30, 0.7);
              --bg-surface: rgba(20, 20, 35, 0.5);
              --text-primary: #ffffff;
              --text-secondary: rgba(255, 255, 255, 0.7);
              --text-tertiary: rgba(255, 255, 255, 0.5);
              --accent-green: #05ffa1;
              --accent-purple: #b967ff;
              --accent-blue: #0162ff;
              --font-heading: 'Syne', sans-serif;
              --font-body: 'Space Grotesk', sans-serif;
              --transition-fast: 0.2s ease;
              --transition-medium: 0.3s ease;
              --transition-slow: 0.5s ease;
            }
            
            body {
              font-family: var(--font-body);
              background-color: var(--bg-dark);
              color: var(--text-primary);
              margin: 0;
              padding: 0;
              line-height: 1.6;
              overflow-x: hidden;
            }
            
            /* Terminal styles - fallback */
            .terminal {
              position: absolute;
              width: 350px;
              background-color: rgba(10, 10, 25, 0.8);
              backdrop-filter: blur(10px);
              border-radius: 0.5rem;
              border: 1px solid rgba(0, 247, 255, 0.2);
              padding: 1rem;
              top: 60%;
              right: 25%;
              transform: rotate(-8deg);
              box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
              z-index: 1;
            }
            
            .terminal-header {
              display: flex;
              align-items: center;
              margin-bottom: 1rem;
              border-bottom: 1px solid rgba(255, 255, 255, 0.1);
              padding-bottom: 0.5rem;
            }
            
            .terminal-title {
              font-family: monospace;
              color: rgba(255, 255, 255, 0.5);
              font-size: 0.8rem;
            }
            
            .terminal-content {
              font-family: monospace;
              font-size: 0.9rem;
              line-height: 1.5;
              color: rgba(255, 255, 255, 0.7);
            }
            
            .prompt {
              color: var(--primary);
              margin-right: 0.25rem;
            }
            
            .command {
              color: var(--text-primary);
            }
            
            .output {
              color: var(--accent-green);
              margin-top: 0.25rem;
              margin-bottom: 0.5rem;
              white-space: pre-wrap;
            }
            
            .pulse {
              animation: pulse 2s ease-in-out infinite;
            }
            
            @keyframes pulse {
              0%, 100% { opacity: 1; }
              50% { opacity: 0.7; }
            }
            
            /* Code window styles - fallback */
            .code-window {
              position: absolute;
              width: 400px;
              background-color: rgba(10, 10, 25, 0.7);
              backdrop-filter: blur(10px);
              border-radius: 0.5rem;
              border: 1px solid rgba(0, 247, 255, 0.2);
              padding: 1rem;
              transform: rotate(5deg);
              top: 30%;
              right: 15%;
              box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
              z-index: 1;
            }
        """.trimIndent()
    }
}