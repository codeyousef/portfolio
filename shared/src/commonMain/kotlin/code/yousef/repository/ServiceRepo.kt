package code.yousef.repository

import code.yousef.model.Service
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ServiceRepo {
    suspend fun findServiceById(id: Uuid): Service?
    suspend fun findFeaturedServices(): List<Service>
    suspend fun saveService(service: Service): Service
    suspend fun updateService(service: Service): Service
    suspend fun getAllServices(): List<Service>
    suspend fun deleteService(id: Uuid): Boolean
    suspend fun findServicesByOrderedByDisplay(): List<Service>
} 