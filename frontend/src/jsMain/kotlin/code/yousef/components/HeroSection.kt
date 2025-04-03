package code.yousef.components

import kotlinx.html.*

/**
 * Hero section component matching the styles from styles.css
 * Uses direct inline styles with HTML string attributes
 */
fun FlowContent.heroSection(
    onViewProjectsClick: () -> Unit = {},
    onContactClick: () -> Unit = {}
) {
    // Define colors as constants for reuse
    val primaryColor = "#00f7ff"
    val primaryColorDark = "#00c3c9"
    val primaryRgb = "0, 247, 255"
    val secondaryColor = "#ff2a6d"
    val secondaryColorDark = "#d31557"
    val secondaryRgb = "255, 42, 109"
    val bgDark = "#0a0a14"

    // Hero section container
    section {
        id = "hero"
        classes = setOf("hero")
        style = """
            min-height: 100vh;
            display: flex;
            align-items: center;
            padding: 4rem;
            position: relative;
            overflow: hidden;
        """

        // Hero content
        div {
            classes = setOf("hero-content", "fade-in")
            style = """
                max-width: 700px;
                z-index: 1;
            """

            // Hero title with gradient
            h1 {
                style = """
                    font-family: 'Syne', sans-serif;
                    font-size: 4rem;
                    font-weight: 700;
                    margin-bottom: 1rem;
                    line-height: 1.1;
                    background: linear-gradient(90deg, $primaryColor, $secondaryColor);
                    -webkit-background-clip: text;
                    background-clip: text;
                    color: transparent;
                """
                +"Crafting Digital Experiences With Cutting-Edge Tech"
            }

            // Hero description
            p {
                style = """
                    font-size: 1.2rem;
                    color: rgba(255, 255, 255, 0.7);
                    margin-bottom: 2rem;
                    max-width: 600px;
                """
                +"Software developer specializing in Kotlin, Quarkus, and modern web technologies. Let's build the future together."
            }

            // CTA buttons
            div {
                // View Projects button
                a(href = "#projects") {
                    classes = setOf("cta-button")
                    style = """
                        display: inline-block;
                        padding: 0.5rem 2rem;
                        background: linear-gradient(90deg, $primaryColor, $primaryColorDark);
                        color: $bgDark;
                        font-weight: 600;
                        text-decoration: none;
                        border-radius: 0.5rem;
                        transition: transform 0.2s ease, box-shadow 0.2s ease;
                        border: none;
                        cursor: pointer;
                        font-family: 'Space Grotesk', sans-serif;
                        font-size: 1rem;
                    """

                    // Add hover effect with JavaScript
                    attributes["onmouseover"] =
                        "this.style.transform='translateY(-2px)'; this.style.boxShadow='0 5px 15px rgba($primaryRgb, 0.3)';"
                    attributes["onmouseout"] = "this.style.transform=''; this.style.boxShadow='';"

                    // Add click handler
                    attributes["onclick"] = "event.preventDefault(); scrollToElement('#projects');"

                    +"View Projects"
                }

                // Contact button
                a(href = "#contact") {
                    classes = setOf("cta-button", "secondary")
                    style = """
                        display: inline-block;
                        padding: 0.5rem 2rem;
                        background: linear-gradient(90deg, $secondaryColor, $secondaryColorDark);
                        color: $bgDark;
                        font-weight: 600;
                        text-decoration: none;
                        border-radius: 0.5rem;
                        transition: transform 0.2s ease, box-shadow 0.2s ease;
                        border: none;
                        cursor: pointer;
                        font-family: 'Space Grotesk', sans-serif;
                        font-size: 1rem;
                        margin-left: 1rem;
                    """

                    // Add hover effect with JavaScript
                    attributes["onmouseover"] =
                        "this.style.transform='translateY(-2px)'; this.style.boxShadow='0 5px 15px rgba($secondaryRgb, 0.3)';"
                    attributes["onmouseout"] = "this.style.transform=''; this.style.boxShadow='';"

                    // Add click handler
                    attributes["onclick"] = "event.preventDefault(); scrollToElement('#contact');"

                    +"Get In Touch"
                }
            }
        }

        // Decorative elements
        // Primary glow effect
        div {
            classes = setOf("hero-glow")
            style = """
                position: absolute;
                top: 20%;
                right: 10%;
                width: 600px;
                height: 600px;
                background: radial-gradient(circle, rgba($primaryRgb, 0.1) 0%, transparent 70%);
                border-radius: 50%;
                filter: blur(50px);
                z-index: 0;
                pointer-events: none;
            """
        }

        // Secondary glow effect
        div {
            classes = setOf("hero-glow-2")
            style = """
                position: absolute;
                bottom: 10%;
                left: 5%;
                width: 400px;
                height: 400px;
                background: radial-gradient(circle, rgba($secondaryRgb, 0.1) 0%, transparent 70%);
                border-radius: 50%;
                filter: blur(50px);
                z-index: 0;
                pointer-events: none;
            """
        }

        // Add floating elements container
        div {
            classes = setOf("floating-elements")
            style = """
                position: absolute;
                top: 0;
                right: 0;
                width: 50%;
                height: 100%;
                z-index: 0;
                overflow: hidden;
                opacity: 0.7;
                pointer-events: none;
            """

            // Add code window
            codeWindow()

            // Add terminal
            terminal()
        }

        // Add scroll utility script
        script {
            unsafe {
                +"""
                    function scrollToElement(selector) {
                        const element = document.querySelector(selector);
                        if (element) {
                            element.scrollIntoView({ behavior: 'smooth' });
                        }
                    }
                """
            }
        }
    }
}

/**
 * Helper function for the floating code window
 */
private fun DIV.codeWindow() {
    val primaryRgb = "0, 247, 255"

    div {
        classes = setOf("code-window", "floating")
        style = """
            position: absolute;
            width: 400px;
            background-color: rgba(10, 10, 25, 0.7);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border-radius: 0.5rem;
            border: 1px solid rgba($primaryRgb, 0.2);
            padding: 1rem;
            transform: rotate(5deg);
            top: 30%;
            right: 15%;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
        """

        // Window header
        div {
            classes = setOf("code-window-header")
            style = """
                display: flex;
                align-items: center;
                margin-bottom: 1rem;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                padding-bottom: 0.5rem;
            """

            // Window dots
            for (color in listOf("#ff5f56", "#ffbd2e", "#27c93f")) {
                div {
                    classes = setOf("window-dot")
                    style = """
                        width: 10px;
                        height: 10px;
                        border-radius: 50%;
                        background-color: $color;
                        margin-right: 0.25rem;
                    """
                }
            }

            // Window title
            div {
                classes = setOf("code-window-title")
                style = """
                    margin-left: 0.5rem;
                    font-size: 0.8rem;
                    color: rgba(255, 255, 255, 0.5);
                """
                +"main.kt"
            }
        }

        // Code content
        div {
            classes = setOf("code-content")
            style = """
                font-family: monospace;
                font-size: 0.9rem;
                line-height: 1.5;
                color: rgba(255, 255, 255, 0.7);
            """

            // Code lines
            codeLine("fun main() {")
            codeLine("  val developer = Developer(", indent = 1)
            codeLine("    name = \"Yousef\",", indent = 2)
            codeLine("    skills = listOf(\"Kotlin Multiplatform\", \"Quarkus/Spring Boot\", \"VueJS\")", indent = 2)
            codeLine("  )", indent = 1)
            codeLine("  ", indent = 1)
            codeLine("  developer.createAmazingSoftware()", indent = 1)
            codeLine("}")
        }
    }
}

/**
 * Helper function for the floating terminal
 */
private fun DIV.terminal() {
    val primaryRgb = "0, 247, 255"
    val primaryColor = "#00f7ff"
    val accentGreen = "#05ffa1"

    div {
        classes = setOf("terminal", "pulse")
        style = """
            position: absolute;
            width: 350px;
            background-color: rgba(10, 10, 25, 0.8);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border-radius: 0.5rem;
            border: 1px solid rgba($primaryRgb, 0.2);
            padding: 1rem;
            top: 60%;
            right: 25%;
            transform: rotate(-8deg);
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
        """

        // Terminal header
        div {
            classes = setOf("terminal-header")
            style = """
                display: flex;
                align-items: center;
                margin-bottom: 1rem;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                padding-bottom: 0.5rem;
            """

            div {
                classes = setOf("terminal-title")
                style = """
                    font-family: monospace;
                    color: rgba(255, 255, 255, 0.5);
                    font-size: 0.8rem;
                """
                +"~/projects $ "
            }
        }

        // Terminal content
        div {
            classes = setOf("terminal-content")
            style = """
                font-family: monospace;
                font-size: 0.9rem;
                line-height: 1.5;
                color: rgba(255, 255, 255, 0.7);
            """

            // Command line
            div {
                span {
                    classes = setOf("prompt")
                    style = """
                        color: $primaryColor;
                        margin-right: 0.25rem;
                    """
                    +"$"
                }

                span {
                    classes = setOf("command")
                    style = "color: white;"
                    +"run portfolio.sh"
                }
            }

            // Output
            div {
                classes = setOf("output")
                style = """
                    color: $accentGreen;
                    margin-top: 0.25rem;
                    margin-bottom: 0.5rem;
                """
                unsafe {
                    +"Initializing project environment...<br>Compiling Kotlin code...<br>Setting up API endpoints...<br>Loading 3D models...<br><br>Portfolio deployed successfully at port 8080!"
                }
            }

            // Prompt
            div {
                span {
                    classes = setOf("prompt")
                    style = "color: $primaryColor;"
                    +"$"
                }

                span {
                    classes = setOf("command")
                    style = "color: white;"
                    +"_"
                }
            }
        }
    }
}

/**
 * Helper function for code lines with syntax highlighting
 */
private fun DIV.codeLine(text: String, indent: Int = 0) {
    div {
        classes = setOf("code-line")
        style = "margin-bottom: 0.25rem;"

        // Add indentation
        val indentation = "  ".repeat(indent)
        +indentation

        // Replace elements with styled spans - simple syntax highlighting
        val formattedText = formatCodeText(text)
        unsafe { +formattedText }
    }
}

/**
 * Helper function to format code with syntax highlighting
 */
private fun formatCodeText(text: String): String {
    return text
        .replace(Regex("(val|fun|var)\\s"), "<span style='color: #ff2a6d;'>$1</span> ")
        .replace(Regex("(\\w+)\\("), "<span style='color: #00f7ff;'>$1</span>(")
        .replace(Regex("\"([^\"]*)\""), "<span style='color: #05ffa1;'>\"$1\"</span>")
} 