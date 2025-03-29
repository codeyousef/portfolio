package code.yousef.application.usecase.blog

import code.yousef.application.service.BlogService
import code.yousef.presentation.dto.response.BlogListResponse
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class GetBlogsUseCase @Inject constructor(
    private val blogService: BlogService
) {
    suspend fun getAllBlogs(): BlogListResponse {
        val blogs = blogService.getAllBlogs()
        val blogResponses = blogs.map { blogService.toResponse(it) }
        return BlogListResponse(blogResponses, blogs.size)
    }

    suspend fun getPublishedBlogs(page: Int, size: Int): BlogListResponse {
        val blogs = blogService.getPublishedBlogs(page, size)
        val blogResponses = blogs.map { blogService.toResponse(it) }
        return BlogListResponse(blogResponses, blogs.size)
    }

    suspend fun getBlogById(id: UUID): BlogResponse? {
        val blog = blogService.getBlogById(id) ?: return null
        return blogService.toResponse(blog)
    }

    suspend fun getBlogBySlug(slug: String): BlogResponse? {
        val blog = blogService.getBlogBySlug(slug) ?: return null
        return blogService.toResponse(blog)
    }

    suspend fun getBlogsByTag(tag: String, page: Int, size: Int): BlogListResponse {
        val blogs = blogService.getBlogsByTag(tag, page, size)
        val blogResponses = blogs.map { blogService.toResponse(it) }
        return BlogListResponse(blogResponses, blogs.size)
    }
}