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
        MediaQueryStyles(),
        SkillStyles() // Add the new SkillStyles
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
            
            /* Grid lines background */
            .grid-lines {
              position: fixed;
              top: 0;
              left: 0;
              width: 100%;
              height: 100%;
              background-size: 50px 50px;
              background-image: linear-gradient(to right, rgba(0, 247, 255, 0.03) 1px, transparent 1px),
                                linear-gradient(to bottom, rgba(0, 247, 255, 0.03) 1px, transparent 1px);
              z-index: -1;
              pointer-events: none;
            }
            
            /* Navigation */
            .navbar {
              position: fixed;
              top: 0;
              left: 0;
              width: 100%;
              display: flex;
              justify-content: space-between;
              align-items: center;
              padding: 1rem 4rem;
              background: rgba(10, 10, 20, 0.8);
              backdrop-filter: blur(10px);
              z-index: 1000;
              border-bottom: 1px solid rgba(0, 247, 255, 0.1);
            }
            
            /* Project cards */
            .projects-grid {
              display: grid;
              grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
              gap: 2rem;
            }
            
            .project-card {
              position: relative;
              border-radius: 1rem;
              overflow: hidden;
              height: 400px;
              cursor: pointer;
              transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            
            .project-card:hover {
              transform: translateY(-10px);
              box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2);
            }
            
            /* Skills */
            .tech-circuit {
              position: relative;
              max-width: 1100px;
              margin: 0 auto;
              padding: 30px 0;
              min-height: 500px;
            }
            
            .skill-cards-container {
              display: grid;
              grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
              gap: 30px;
              position: relative;
              z-index: 2;
            }
            
            .skill-card {
              perspective: 1000px;
              height: 280px;
            }
            
            .skill-card-inner {
              position: relative;
              width: 100%;
              height: 100%;
              text-align: center;
              transition: transform 0.8s;
              transform-style: preserve-3d;
            }
            
            .skill-card:hover .skill-card-inner {
              transform: rotateY(180deg);
            }
        """.trimIndent()
    }
}