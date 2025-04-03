package code.yousef.components

import kotlinx.html.*

/**
 * Blog card component
 */
fun FlowContent.blogCard(
    image: String,
    title: String,
    excerpt: String,
    date: String,
    readTime: String,
    link: String
) {
    article {
        classes = setOf("blog-card")

        div {
            classes = setOf("blog-image")
            img {
                src = image
                alt = title
            }
        }

        div {
            classes = setOf("blog-content")

            div {
                classes = setOf("blog-meta")
                span {
                    classes = setOf("blog-date")
                    +date
                }

                span {
                    classes = setOf("blog-read-time")
                    +readTime
                }
            }

            h3 {
                classes = setOf("blog-title")
                +title
            }

            p {
                classes = setOf("blog-excerpt")
                +excerpt
            }

            a {
                href = link
                classes = setOf("blog-read-more")
                +"Read More"
            }
        }
    }
} 