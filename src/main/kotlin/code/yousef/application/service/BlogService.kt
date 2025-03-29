package code.yousef.application.service

import code.yousef.domain.model.BlogPost
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import code.yousef.infrastructure.persistence.repository.BlogRepoImpl
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.*

@ApplicationScoped
class BlogService @Inject constructor(
    private val blogRepo: BlogRepoImpl,
    private val blogMapper: BlogPostMapper
) {
    suspend fun getAllBlogs(): List<BlogPost> {
        return blogRepo.getAllBlogs()
    }

    suspend fun getPublishedBlogs(page: Int, size: Int): List<BlogPost> {
        return blogRepo.findPublishedBlogs(page, size)
    }

    suspend fun getBlogById(id: UUID): BlogPost? {
        return blogRepo.findBlogById(id)
    }

    suspend fun getBlogBySlug(slug: String): BlogPost? {
        return blogRepo.findBySlug(slug)
    }

    suspend fun getBlogsByTag(tag: String, page: Int, size: Int): List<BlogPost> {
        return blogRepo.findByTag(tag, page, size)
    }

    suspend fun createBlog(request: CreateUpdateBlogRequest): BlogPost {
        val blog = blogMapper.toDomain(request, null, blogRepo)
        return blogRepo.saveBlog(blog)
    }

    suspend fun updateBlog(id: UUID, request: CreateUpdateBlogRequest): BlogPost? {
        val existingBlog = blogRepo.findBlogById(id) ?: return null
        val updatedBlog = blogMapper.toDomain(request, existingBlog, blogRepo)
        return blogRepo.saveBlog(updatedBlog)
    }

    suspend fun togglePublished(id: UUID): BlogPost? {
        val blog = blogRepo.findBlogById(id) ?: return null
        val updatedBlog = blog.withUpdatedFields(published = !blog.published)
        return blogRepo.saveBlog(updatedBlog)
    }

    suspend fun deleteBlog(id: UUID): Boolean {
        return blogRepo.deleteBlog(id)
    }

    fun toResponse(blog: BlogPost): BlogResponse {
        return blogMapper.toResponse(blog)
    }
}