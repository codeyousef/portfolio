package code.yousef.repository

import code.yousef.model.BlogPost
import java.util.UUID

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