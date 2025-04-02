package code.yousef.application.service

import code.yousef.model.Service
import code.yousef.repository.ServiceRepo
import code.yousef.infrastructure.persistence.repository.ServiceRepoImpl
import code.yousef.infrastructure.persistence.mapper.ServiceMapper
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class ServiceService @Inject constructor(
    private val serviceRepo: ServiceRepoImpl,
    private val serviceMapper: ServiceMapper
) {
    suspend fun getAllServices(): List<Service> {
        return serviceRepo.getAllServices()
    }

    suspend fun getFeaturedServices(): List<Service> {
        return serviceRepo.findFeaturedServices()
    }

    suspend fun getServiceById(id: Uuid): Service? {
        return serviceRepo.findServiceById(id)
    }

    suspend fun createService(request: CreateUpdateServiceRequest): Service {
        val service = Service(
            id = "",
            title = request.title ?: "",
            shortDescription = request.shortDescription ?: "",
            fullDescription = request.fullDescription ?: "",
            iconClass = request.iconClass ?: "",
            price = request.price?.toDoubleOrNull(),
            features = request.features ?: emptyList(),
            ctaText = request.ctaText ?: "Hire Me",
            ctaLink = request.ctaLink ?: "/contact",
            displayOrder = request.displayOrder ?: 0,
            featured = request.featured ?: false,
            createdAt = Clock.System.now().toString(),
            updatedAt = Clock.System.now().toString(),
            detailsLink = request.detailsLink ?: ""
        )
        
        return serviceRepo.saveService(service)
    }

    suspend fun updateService(id: Uuid, request: CreateUpdateServiceRequest): Service? {
        val existingService = serviceRepo.findServiceById(id) ?: return null
        
        val updatedService = existingService.copy(
            title = request.title ?: existingService.title,
            shortDescription = request.shortDescription ?: existingService.shortDescription,
            fullDescription = request.fullDescription ?: existingService.fullDescription,
            iconClass = request.iconClass ?: existingService.iconClass,
            price = request.price?.toDoubleOrNull() ?: existingService.price,
            features = request.features ?: existingService.features,
            ctaText = request.ctaText ?: existingService.ctaText,
            ctaLink = request.ctaLink ?: existingService.ctaLink,
            displayOrder = request.displayOrder ?: existingService.displayOrder,
            featured = request.featured ?: existingService.featured,
            detailsLink = request.detailsLink ?: existingService.detailsLink,
            updatedAt = Clock.System.now().toString()
        )
        
        return serviceRepo.updateService(updatedService)
    }

    suspend fun deleteService(id: Uuid): Boolean {
        return serviceRepo.deleteService(id)
    }

    suspend fun getServicesByDisplayOrder(): List<Service> {
        return serviceRepo.findServicesByOrderedByDisplay()
    }

    suspend fun toggleFeatured(id: Uuid): Service? {
        val service = serviceRepo.findServiceById(id) ?: return null
        val updatedService = service.copy(
            featured = !service.featured,
            updatedAt = Clock.System.now().toString()
        )
        return serviceRepo.updateService(updatedService)
    }

    fun toResponse(service: Service): ServiceResponse {
        val uuid = if (service.id.isNotEmpty()) {
            try {
                Uuid.parse(service.id)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
        
        return ServiceResponse(
            id = uuid,
            title = service.title,
            shortDescription = service.shortDescription,
            fullDescription = service.fullDescription,
            iconClass = service.iconClass,
            price = service.price?.toString(),
            features = service.features,
            ctaText = service.ctaText,
            ctaLink = service.ctaLink,
            displayOrder = service.displayOrder,
            featured = service.featured,
            createdAt = service.createdAt,
            updatedAt = service.updatedAt,
            detailsLink = service.detailsLink
        )
    }
}
