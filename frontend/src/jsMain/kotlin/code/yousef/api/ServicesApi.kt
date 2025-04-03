package code.yousef.api

import code.yousef.model.Service

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