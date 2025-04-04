package code.yousef.application.service

import code.yousef.infrastructure.persistence.mapper.ServiceMapper
import code.yousef.infrastructure.persistence.repository.ServiceRepoImpl
import code.yousef.model.Service
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.time.LocalDateTime
import java.util.*

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

    suspend fun getServiceById(id: UUID): Service? {
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
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            detailsLink = request.detailsLink ?: ""
        )

        return serviceRepo.saveService(service)
    }

    suspend fun updateService(id: UUID, request: CreateUpdateServiceRequest): Service? {
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
            updatedAt = LocalDateTime.now()
        )

        return serviceRepo.updateService(updatedService)
    }

    suspend fun deleteService(id: UUID): Boolean {
        return serviceRepo.deleteService(id)
    }

    suspend fun getServicesByDisplayOrder(): List<Service> {
        return serviceRepo.findServicesByOrderedByDisplay()
    }

    suspend fun toggleFeatured(id: UUID): Service? {
        val service = serviceRepo.findServiceById(id) ?: return null
        val updatedService = service.copy(
            featured = !service.featured,
            updatedAt = LocalDateTime.now()
        )
        return serviceRepo.updateService(updatedService)
    }

    fun toResponse(service: Service): ServiceResponse {

        return ServiceResponse(
            id = UUID.fromString(service.id),
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
            createdAt = service.createdAt.toString(),
            updatedAt = service.updatedAt.toString(),
            detailsLink = service.detailsLink
        )
    }
}
