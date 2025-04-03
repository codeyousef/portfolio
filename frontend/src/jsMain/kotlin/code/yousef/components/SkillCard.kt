package code.yousef.components

import kotlinx.html.*

/**
 * Skill card component that matches the styles.css design
 * Using direct inline styles with HTML string attributes
 */
fun FlowContent.skillCard(
    name: String, 
    subtitle: String, 
    logoUrl: String? = null,
    details: List<String> = emptyList()
) {
    // Define all colors as constants for reuse
    val primaryColor = "#00f7ff"
    val primaryRgb = "0, 247, 255"
    val accentPurple = "#b967ff"
    
    div {
        // Add classes for animation and identification
        classes = setOf("skill-card", "fade-in")
        
        // Apply skill card styles
        style = """
            background: rgba(16, 16, 30, 0.7);
            border-radius: 0.5rem;
            overflow: hidden;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            border: 1px solid rgba($primaryRgb, 0.1);
            position: relative;
        """
        
        // Add gradient border at top using pseudo-element via CSS custom property
        attributes["style"] = style + """
            ;--after-content: '';
            --after-position: absolute;
            --after-top: 0;
            --after-left: 0;
            --after-right: 0;
            --after-height: 3px;
            --after-background: linear-gradient(90deg, $primaryColor, $accentPurple);
            --after-opacity: 0.8;
            --after-z-index: 1;
        """
        
        // Add hover effect with JavaScript
        attributes["onmouseover"] = "this.style.transform='translateY(-5px)'; this.style.boxShadow='0 8px 30px rgba(0, 0, 0, 0.3)'; this.style.borderColor='rgba($primaryRgb, 0.3)';"
        attributes["onmouseout"] = "this.style.transform=''; this.style.boxShadow='0 4px 20px rgba(0, 0, 0, 0.2)'; this.style.borderColor='rgba($primaryRgb, 0.1)';"
        
        // Inner content container
        div {
            style = """
                display: flex;
                padding: 2rem;
                align-items: center;
                gap: 2rem;
            """
            
            // Skill icon
            div {
                style = """
                    width: 60px;
                    height: 60px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    border-radius: 0.5rem;
                    background: rgba($primaryRgb, 0.1);
                    flex-shrink: 0;
                """
                
                if (logoUrl != null) {
                    img(src = logoUrl, alt = "$name logo") {
                        style = """
                            max-width: 40px;
                            max-height: 40px;
                            object-fit: contain;
                        """
                        
                        // Add error handling for images
                        attributes["onerror"] = """
                            this.onerror=null;
                            this.parentElement.innerHTML = '<div style="display:flex; align-items:center; justify-content:center; background:rgba($primaryRgb,0.1); border-radius: 50%; color: $primaryColor; font-size: 2rem; font-weight: bold; width: 80px; height: 80px; margin-bottom: 20px; filter: drop-shadow(0 0 10px rgba($primaryRgb, 0.7));">${name.firstOrNull() ?: '?'}</div>';
                        """
                    }
                } else {
                    // Fallback icon with first letter
                    div {
                        style = """
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            font-size: 1.8rem;
                            font-weight: bold;
                            color: $primaryColor;
                            width: 100%;
                            height: 100%;
                        """
                        +(name.firstOrNull()?.toString() ?: "?")
                    }
                }
            }
            
            // Skill content
            div {
                style = "flex: 1;"
                
                // Skill title
                h3 {
                    style = """
                        font-family: 'Syne', sans-serif;
                        font-size: 1.5rem;
                        font-weight: 600;
                        margin-bottom: 0.25rem;
                        color: white;
                    """
                    +name
                }
                
                // Skill subtitle
                p {
                    style = """
                        color: rgba(255, 255, 255, 0.7);
                        margin-bottom: 1rem;
                        font-size: 0.9rem;
                    """
                    +subtitle
                }
                
                // Skill details list
                if (details.isNotEmpty()) {
                    ul {
                        style = "list-style: none; padding: 0;"
                        
                        details.forEach { detail ->
                            li {
                                style = """
                                    color: rgba(255, 255, 255, 0.5);
                                    font-size: 0.85rem;
                                    margin-bottom: 0.25rem;
                                    position: relative;
                                    padding-left: 1rem;
                                """
                                
                                // Add bullet point with custom color
                                span {
                                    style = """
                                        position: absolute;
                                        left: 0;
                                        color: $primaryColor;
                                    """
                                    +"•"
                                }
                                
                                // Detail text
                                span {
                                    +detail
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Usage example:
 * 
 * skillsGrid {
 *     val skills = listOf(
 *         Triple("Kotlin", "Modern JVM Language", listOf("Concise, safe, and expressive", "Coroutines for async code", "Multiplatform")),
 *         Triple("Quarkus", "Cloud Native Java", listOf("Fast startup & low memory", "Developer joy", "Kubernetes native"))
 *     )
 *     
 *     skills.forEach { (name, subtitle, details) ->
 *         skillCard(name, subtitle, details = details)
 *     }
 * }
 */
fun FlowContent.skillsGrid(block: DIV.() -> Unit) {
    div {
        style = """
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 4rem;
        """
        classes = setOf("skills-grid")
        
        block()
    }
} 