package code.yousef.infrastructure.template

import code.yousef.infrastructure.persistence.entity.BlogPost
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.article
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.stream.appendHTML

@ApplicationScoped
class BlogTemplates {

    @Location("blog/base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    /**
     * Builds the blog listing page by combining Kotlin HTML DSL with Qute
     */
    fun buildBlogPage(blogPosts: List<BlogPost>, page: Int, pageSize: Int): TemplateInstance {
        // Generate the HTML content using the original approach
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().div {
            // Your original blog page HTML generation logic here
            // This preserves all your existing functionality
            h1 { +"Blog Posts" }

            div(classes = "blog-list") {
                blogPosts.forEach() { post ->
                        article {
                            h2 { +post.title }
                            // Other post content
                        }
                    }
                }

            // Pagination
            div(classes = "pagination") {
                // Pagination controls
            }
        }

        // Use Qute template for the base layout
        return baseTemplate
            .data("title", "Blog - Page $page")
            .data("content", contentBuilder.toString())
    }

    /**
     * Builds a single blog post page using Kotlin HTML DSL wrapped in Qute
     */
    fun buildBlogPostPage(blogPost: BlogPost?): TemplateInstance {
        // Generate the post content using the original approach
        val postContent = StringBuilder()
        postContent.appendHTML().article {
            h1 {
                if (blogPost != null) {
                    +blogPost.title
                }
            }
            // Other blog post display logic
        }

        // Use Qute template for the base layout
        return if (blogPost != null) {
            baseTemplate
                .data("title", blogPost.title)
                .data("content", postContent.toString())
        } else {
            buildNotFoundPage()
        }
    }

    fun buildNotFoundPage(): TemplateInstance {
        TODO("Not yet implemented")
    }
}