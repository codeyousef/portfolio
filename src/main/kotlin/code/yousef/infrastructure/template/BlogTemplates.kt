package code.yousef.infrastructure.template

import code.yousef.presentation.dto.response.BlogResponse
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML

@ApplicationScoped
class BlogTemplates {
    @Location("blog/base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    fun buildBlogPage(blogPosts: List<BlogResponse>, page: Int, pageSize: Int): TemplateInstance {
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().div {
            h1 { +"Blog Posts" }

            div(classes = "blog-list") {
                blogPosts.forEach { post ->
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

        return baseTemplate
            .data("title", "Blog - Page $page")
            .data("content", contentBuilder.toString())
    }

    fun buildBlogPostPage(blogPost: BlogResponse?): TemplateInstance {
        val postContent = StringBuilder()
        postContent.appendHTML().article {
            h1 {
                if (blogPost != null) {
                    +blogPost.title
                }
            }
            // Other blog post display logic
        }

        return if (blogPost != null) {
            baseTemplate
                .data("title", blogPost.title)
                .data("content", postContent.toString())
        } else {
            buildNotFoundPage()
        }
    }

    fun buildNotFoundPage(): TemplateInstance {
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().div {
            h1 { +"Page Not Found" }
            div { +"The blog post you're looking for doesn't exist or has been removed." }
        }

        return baseTemplate
            .data("title", "Not Found")
            .data("content", contentBuilder.toString())
    }
}