package code.yousef.components

import kotlinx.html.*

/**
 * Service card component
 */
fun FlowContent.serviceCard(
    icon: String,
    title: String,
    description: String
) {
    div {
        classes = setOf("service-card")

        div {
            classes = setOf("service-icon")
            i {
                classes = setOf("icon", icon)
            }
        }

        h3 {
            classes = setOf("service-title")
            +title
        }

        p {
            classes = setOf("service-description")
            +description
        }
    }
}

/**
 * Services grid component for displaying multiple service cards
 */
fun FlowContent.servicesGrid(block: DIV.() -> Unit) {
    div {
        style = """
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        """
        classes = setOf("services-grid")

        block()
    }
} 