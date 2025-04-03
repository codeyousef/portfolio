package code.yousef.api

import code.yousef.model.BlogPost

/**
 * Blog API client
 */
object BlogApi {
    /**
     * Get all blog posts
     */
    suspend fun getAllBlogPosts(page: Int = 0, size: Int = 10): List<BlogPost> {
        val endpoint = "${ApiRoutes.Blog.BASE}?page=$page&size=$size"
        return ApiClient.get<List<BlogPost>>(endpoint) ?: emptyList()
    }

    /**
     * Get blog post by slug
     */
    suspend fun getBlogPostBySlug(slug: String): BlogPost? {
        val endpoint = ApiRoutes.Blog.BY_SLUG.replace("{slug}", slug)
        return ApiClient.get<BlogPost>(endpoint)
    }

    /**
     * Get blog posts by tag
     */
    suspend fun getBlogPostsByTag(tag: String, page: Int = 0, size: Int = 10): List<BlogPost> {
        val endpoint = "${ApiRoutes.Blog.BY_TAG.replace("{tag}", tag)}?page=$page&size=$size"
        return ApiClient.get<List<BlogPost>>(endpoint) ?: emptyList()
    }
}