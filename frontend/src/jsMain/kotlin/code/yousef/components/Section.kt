package code.yousef.components

import kotlinx.html.*

/**
 * Section component matching the styles from styles.css
 * Using direct inline styles with HTML string attributes
 */
fun FlowContent.section(
    id: String? = null,
    classes: String? = null,
    style: String? = null,
    block: DIV.() -> Unit
) {
    section {
        // Set the ID if provided
        if (id != null) {
            this.id = id
        }

        // Set classes
        val classList = mutableSetOf("section")
        if (classes != null) {
            classList.addAll(classes.split(" "))
        }
        this.classes = classList

        // Apply base styles
        this.style = """
            padding: 4rem 0;
            position: relative;
        """

        // Apply additional styles if provided
        if (style != null) {
            this.style += "; $style"
        }

        // Apply the content block
        block()
    }
}

/**
 * Container component matching the styles from styles.css
 * Using direct inline styles with HTML string attributes
 */
fun FlowContent.container(
    classes: String? = null,
    style: String? = null,
    block: DIV.() -> Unit
) {
    div {
        // Set classes
        val classList = mutableSetOf("container")
        if (classes != null) {
            classList.addAll(classes.split(" "))
        }
        this.classes = classList

        // Apply base styles
        this.style = """
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
        """

        // Apply additional styles if provided
        if (style != null) {
            this.style += "; $style"
        }

        // Apply the content block
        block()
    }
}

/**
 * Section header component matching the styles from styles.css
 * Using direct inline styles with HTML string attributes
 */
fun DIV.sectionHeader(
    title: String,
    description: String? = null,
    titleClass: String? = null
) {
    // Define colors
    val primaryColor = "#00f7ff"
    val accentPurple = "#b967ff"

    div {
        classes = setOf("section-header")
        style = """
            text-align: center;
            margin-bottom: 4rem;
            position: relative;
        """

        h2 {
            // Set classes
            val classList = mutableSetOf("section-title")
            if (titleClass != null) {
                classList.addAll(titleClass.split(" "))
            }
            classes = classList

            // Apply styles with gradient text
            style = """
                font-family: 'Syne', sans-serif;
                font-size: 2.5rem;
                font-weight: 700;
                margin-bottom: 1rem;
                background: linear-gradient(to right, $primaryColor, $accentPurple);
                -webkit-background-clip: text;
                background-clip: text;
                -webkit-text-fill-color: transparent;
                display: inline-block;
            """

            +title
        }

        if (description != null) {
            p {
                classes = setOf("section-desc")
                style = """
                    font-size: 1.1rem;
                    color: rgba(255, 255, 255, 0.7);
                    max-width: 600px;
                    margin: 0 auto;
                """
                +description
            }
        }
    }
}

/**
 * Usage example:
 *
 * section(id = "projects") {
 *     container {
 *         sectionHeader(
 *             title = "Featured Projects",
 *             description = "Explore my latest work showcasing innovative solutions and cutting-edge technologies."
 *         )
 *
 *         div {
 *             classes = setOf("projects-grid")
 *             style = "display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 2rem;"
 *
 *             projects.forEach { project ->
 *                 projectCard(project)
 *             }
 *         }
 *     }
 * }
 */ 