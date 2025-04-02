package code.yousef.infrastructure.template

import code.yousef.presentation.dto.response.BlogResponse
import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@ApplicationScoped
class BlogTemplates {
    @Location("blog/base-layout.html")
    @Inject
    lateinit var baseTemplate: Template

    fun buildBlogPage(blogPosts: List<BlogResponse>, page: Int, pageSize: Int, totalPages: Int = 1): TemplateInstance {
        val contentBuilder = StringBuilder()
        contentBuilder.appendHTML().apply {
            // Hero section
            section(classes = "blog-hero") {
                div(classes = "blog-hero-content") {
                    h1(classes = "blog-hero-title") { +"Thoughts & Insights" }
                    p(classes = "blog-hero-subtitle") { 
                        +"Exploring software development, technology trends, and innovative solutions."
                    }
                }
            }
            
            // Blog list section
            div(classes = "blog-container") {
                div(classes = "blog-list") {
                    blogPosts.forEach { post ->
                        article(classes = "blog-card") {
                            div(classes = "blog-card-image-container") {
                                img(
                                    src = post.imageUrl.ifEmpty { "/app/images/blog-placeholder.jpg" },
                                    alt = post.title,
                                    classes = "blog-card-image"
                                )
                            }
                            div(classes = "blog-card-content") {
                                div(classes = "blog-card-date") { +formatDate(post.publishDate) }
                                h2(classes = "blog-card-title") { +post.title }
                                div(classes = "blog-card-tags") {
                                    post.tags.take(3).forEach { tag ->
                                        span(classes = "blog-card-tag") { +tag }
                                    }
                                }
                                p(classes = "blog-card-summary") { +post.summary }
                                div(classes = "blog-card-meta") {
                                    div(classes = "blog-card-reading-time") {
                                        i(classes = "fas fa-clock") {}
                                        +" ${post.readingTime} min read"
                                    }
                                }
                                div(classes = "blog-card-link") {
                                    a(href = "/blog/${post.slug}", classes = "blog-card-button") {
                                        +"Read More"
                                        i(classes = "fas fa-arrow-right") {}
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (totalPages > 1) {
                    // Pagination
                    div(classes = "pagination") {
                        val prevDisabled = page <= 1
                        val nextDisabled = page >= totalPages
                        
                        a(
                            href = if (prevDisabled) "#" else "/blog?page=${page - 1}",
                            classes = "pagination-button${if (prevDisabled) " disabled" else ""}"
                        ) {
                            i(classes = "fas fa-chevron-left") {}
                        }
                        
                        // Show up to 5 page numbers
                        val startPage = maxOf(1, minOf(page - 2, totalPages - 4))
                        val endPage = minOf(totalPages, maxOf(page + 2, 5))
                        
                        for (i in startPage..endPage) {
                            a(
                                href = "/blog?page=$i",
                                classes = "pagination-button${if (i == page) " active" else ""}"
                            ) {
                                +i.toString()
                            }
                        }
                        
                        a(
                            href = if (nextDisabled) "#" else "/blog?page=${page + 1}",
                            classes = "pagination-button${if (nextDisabled) " disabled" else ""}"
                        ) {
                            i(classes = "fas fa-chevron-right") {}
                        }
                    }
                }
            }
        }

        return baseTemplate
            .data("title", "Blog - Page $page")
            .data("content", contentBuilder.toString())
    }

    fun buildBlogPostPage(blogPost: BlogResponse?): TemplateInstance {
        val postContent = StringBuilder()
        postContent.appendHTML().apply {
            if (blogPost != null) {
                article(classes = "blog-post") {
                    div(classes = "blog-post-header") {
                        h1(classes = "blog-post-title") { +blogPost.title }
                        div(classes = "blog-post-meta") {
                            div(classes = "blog-post-date") {
                                i(classes = "fas fa-calendar") {}
                                +" ${formatDate(blogPost.publishDate)}"
                            }
                            div(classes = "blog-post-reading-time") {
                                i(classes = "fas fa-clock") {}
                                +" ${blogPost.readingTime} min read"
                            }
                        }
                    }
                    
                    if (blogPost.imageUrl.isNotEmpty()) {
                        img(
                            src = blogPost.imageUrl,
                            alt = blogPost.title,
                            classes = "blog-post-image"
                        )
                    }
                    
                    div(classes = "blog-post-content") {
                        unsafe {
                            +blogPost.content
                        }
                    }
                    
                    if (blogPost.tags.isNotEmpty()) {
                        div(classes = "blog-post-tags") {
                            blogPost.tags.forEach { tag ->
                                span(classes = "blog-post-tag") { +tag }
                            }
                        }
                    }
                }
            }
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
        contentBuilder.appendHTML().div(classes = "not-found") {
            h1 { +"Page Not Found" }
            p { +"The blog post you're looking for doesn't exist or has been removed." }
            a(href = "/blog", classes = "not-found-button") {
                i(classes = "fas fa-arrow-left") {}
                +" Go Back to Blog"
            }
        }

        return baseTemplate
            .data("title", "Not Found")
            .data("content", contentBuilder.toString())
    }
    
    private fun formatDate(dateStr: String): String {
        try {
            val instant = Instant.parse(dateStr)
            val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            val javaLocalDateTime = java.time.LocalDateTime.of(
                localDateTime.year,
                localDateTime.monthNumber,
                localDateTime.dayOfMonth,
                localDateTime.hour,
                localDateTime.minute,
                localDateTime.second,
                localDateTime.nanosecond
            )
            return javaLocalDateTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        } catch (e: Exception) {
            return dateStr
        }
    }
}