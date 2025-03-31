package code.yousef.application.service

import code.yousef.domain.model.Service
import code.yousef.domain.repository.ServiceRepo
import code.yousef.infrastructure.persistence.mapper.ServiceMapper
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.*

@ApplicationScoped
class ServiceService @Inject constructor(
    private val serviceRepo: ServiceRepo,
    private val serviceMapper: ServiceMapper
) {
    suspend fun getAllServices(): List<Service> {
        return serviceRepo.getAllServices()
    }

    suspend fun getFeaturedServices(): List<Service> {
        return serviceRepo.findFeaturedServices()
    }

    suspend fun getServiceById(id: UUID): Service? {
        return serviceRepo.findServiceById(id)
    }

    suspend fun createService(request: CreateUpdateServiceRequest): Service{
        val service = serviceMapper.toDomain(request)
        return serviceRepo.saveService(service)
    }

    suspend fun updateService(id: UUID, request: CreateUpdateServiceRequest): Service? {
        val existingService = serviceRepo.findServiceById(id) ?: return null
        val updatedService = serviceMapper.toDomain(request, existingService)
        return serviceRepo.saveService(updatedService)
    }

    suspend fun deleteService(id: UUID): Boolean {
        return serviceRepo.deleteService(id)
    }

    suspend fun toggleFeatured(id: UUID): Service? {
        val service = serviceRepo.findServiceById(id) ?: return null
        val updatedService = service.withUpdatedFields(featured = !service.featured)
        return serviceRepo.saveService(updatedService)
    }

    suspend fun getOrderedServices(): List<Service> {
        return serviceRepo.findServicesByOrderedByDisplay()
    }

    fun toResponse(service: Service): ServiceResponse {
        return serviceMapper.toResponse(service)
    }
}
