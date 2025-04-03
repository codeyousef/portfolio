package code.yousef.components

import kotlinx.html.*

/**
 * Project card component
 */
fun FlowContent.projectCard(
    image: String,
    title: String,
    description: String,
    technologies: List<String>,
    liveLink: String? = null,
    repoLink: String? = null
) {
    div {
        classes = setOf("project-card")

        div {
            classes = setOf("project-image")
            img {
                src = image
                alt = title
            }
        }

        div {
            classes = setOf("project-content")
            h3 {
                classes = setOf("project-title")
                +title
            }

            p {
                classes = setOf("project-description")
                +description
            }

            div {
                classes = setOf("project-tech")
                technologies.forEach { tech ->
                    span {
                        classes = setOf("tech-tag")
                        +tech
                    }
                }
            }

            div {
                classes = setOf("project-links")
                liveLink?.let {
                    a {
                        href = it
                        target = "_blank"
                        rel = "noopener noreferrer"
                        classes = setOf("btn-sm", "btn-primary")
                        +"Live Demo"
                    }
                }

                repoLink?.let {
                    a {
                        href = it
                        target = "_blank"
                        rel = "noopener noreferrer"
                        classes = setOf("btn-sm", "btn-secondary")
                        +"Source Code"
                    }
                }
            }
        }
    }
} 