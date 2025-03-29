package code.yousef.domain.repository

import code.yousef.domain.model.BlogPost
import java.util.*

interface BlogRepo {
    suspend fun findBlogById(id: UUID): BlogPost?
    suspend fun findBySlug(slug: String): BlogPost?
    suspend fun findPublishedBlogs(page: Int, size: Int): List<BlogPost>
    suspend fun findByTag(tag: String, page: Int, size: Int): List<BlogPost>
    suspend fun saveBlog(blog: BlogPost): BlogPost
    suspend fun getAllBlogs(): List<BlogPost>
    suspend fun deleteBlog(id: UUID): Boolean
    fun generateSlug(title: String): String
}