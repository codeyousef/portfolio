package code.yousef.api

import code.yousef.model.Project

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