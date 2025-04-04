package code.yousef.repository

import code.yousef.model.Service
import java.util.UUID

interface ServiceRepo {
    suspend fun findServiceById(id: UUID): Service?
    suspend fun findFeaturedServices(): List<Service>
    suspend fun saveService(service: Service): Service
    suspend fun updateService(service: Service): Service
    suspend fun getAllServices(): List<Service>
    suspend fun deleteService(id: UUID): Boolean
    suspend fun findServicesByOrderedByDisplay(): List<Service>
} 