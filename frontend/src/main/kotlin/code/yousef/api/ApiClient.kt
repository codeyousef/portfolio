package code.yousef.api

import code.yousef.model.Project
import code.yousef.model.BlogPost
import code.yousef.model.Service
import code.yousef.routes.ApiRoutes
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import kotlin.js.json

/**
 * Base API client for making HTTP requests
 */
object ApiClient {
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Performs a GET request to the specified endpoint
     */
    suspend inline fun <reified T> get(endpoint: String): T? {
        try {
            val response = window.fetch(endpoint, RequestInit()).await()

            if (!response.ok) {
                console.error("API error: ${response.status} ${response.statusText}")
                return null
            }

            val text = response.text().await()
            return json.decodeFromString<T>(text)
        } catch (e: Exception) {
            console.error("API request failed: $e")
            return null
        }
    }

    /**
     * Performs a POST request to the specified endpoint
     */
    suspend inline fun <reified T, reified R> post(endpoint: String, body: T): R? {
        try {
            val response = window.fetch(
                endpoint,
                RequestInit(
                    method = "POST",
                    headers = json("Content-Type" to "application/json"),
                    body = JSON.stringify(body)
                )
            ).await()

            if (!response.ok) {
                console.error("API error: ${response.status} ${response.statusText}")
                return null
            }

            val text = response.text().await()
            return json.decodeFromString<R>(text)
        } catch (e: Exception) {
            console.error("API request failed: $e")
            return null
        }
    }
}

/**
 * Projects API client
 */
object ProjectsApi {
    /**
     * Get all projects
     */
    suspend fun getAllProjects(): List<Project> {
        return ApiClient.get<List<Project>>(ApiRoutes.Projects.BASE) ?: emptyList()
    }

    /**
     * Get featured projects
     */
    suspend fun getFeaturedProjects(): List<Project> {
        return ApiClient.get<List<Project>>(ApiRoutes.Projects.FEATURED) ?: emptyList()
    }

    /**
     * Get project by ID
     */
    suspend fun getProjectById(id: String): Project? {
        val endpoint = ApiRoutes.Projects.BY_ID.replace("{id}", id)
        return ApiClient.get<Project>(endpoint)
    }
}

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

/**
 * Services API client
 */
object ServicesApi {
    /**
     * Get all services
     */
    suspend fun getAllServices(): List<Service> {
        return ApiClient.get<List<Service>>(ApiRoutes.Services.BASE) ?: emptyList()
    }

    /**
     * Get featured services
     */
    suspend fun getFeaturedServices(): List<Service> {
        return ApiClient.get<List<Service>>(ApiRoutes.Services.FEATURED) ?: emptyList()
    }

    /**
     * Get service by ID
     */
    suspend fun getServiceById(id: String): Service? {
        val endpoint = ApiRoutes.Services.BY_ID.replace("{id}", id)
        return ApiClient.get<Service>(endpoint)
    }
}