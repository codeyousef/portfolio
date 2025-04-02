package code.yousef.infrastructure.persistence.mapper

import code.yousef.model.Service
import code.yousef.infrastructure.persistence.entity.ServiceEntity
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class ServiceMapper {

    /**
     * Convert ServiceEntity to Service model
     */
    fun toModel(entity: ServiceEntity): Service {
        return Service(
            id = entity.id.toString(),
            title = entity.title,
            shortDescription = entity.shortDescription,
            fullDescription = entity.fullDescription,
            iconClass = entity.iconClass,
            price = entity.price?.toDoubleOrNull(),
            features = entity.features,
            ctaText = entity.ctaText,
            ctaLink = entity.ctaLink,
            displayOrder = entity.displayOrder,
            featured = entity.featured,
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            detailsLink = entity.detailsLink
        )
    }

    /**
     * Convert Service model to ServiceEntity
     */
    fun toEntity(service: Service): ServiceEntity {
        val entity = ServiceEntity()
        if (service.id.isNotEmpty()) {
            entity.id = UUID.fromString(service.id)
        }
        entity.title = service.title
        entity.shortDescription = service.shortDescription
        entity.fullDescription = service.fullDescription
        entity.iconClass = service.iconClass ?: ""
        entity.price = service.price?.toString()
        entity.features = service.features
        entity.ctaText = service.ctaText ?: "Hire Me"
        entity.ctaLink = service.ctaLink ?: "/contact"
        entity.displayOrder = service.displayOrder
        entity.featured = service.featured
        entity.createdAt = service.createdAt?.let { Instant.parse(it) } ?: Clock.System.now()
        entity.updatedAt = service.updatedAt?.let { Instant.parse(it) } ?: Clock.System.now()
        entity.detailsLink = service.detailsLink ?: "/services/${service.id}"
        return entity
    }

    /**
     * Update an existing ServiceEntity from Service model
     */
    fun updateEntity(entity: ServiceEntity, service: Service): ServiceEntity {
        entity.title = service.title
        entity.shortDescription = service.shortDescription
        entity.fullDescription = service.fullDescription
        entity.iconClass = service.iconClass ?: ""
        entity.price = service.price?.toString()
        entity.features = service.features
        entity.ctaText = service.ctaText ?: "Hire Me"
        entity.ctaLink = service.ctaLink ?: "/contact"
        entity.displayOrder = service.displayOrder
        entity.featured = service.featured
        entity.updatedAt = Clock.System.now()
        entity.detailsLink = service.detailsLink ?: "/services/${service.id}"
        return entity
    }

    /**
     * Convert Service to ServiceResponse
     */
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

    /**
     * Convert CreateUpdateServiceRequest to Service model
     */
    fun toDomain(request: CreateUpdateServiceRequest, existingService: Service? = null): Service {
        return Service(
            id = existingService?.id ?: "",
            title = request.title ?: existingService?.title ?: "",
            shortDescription = request.shortDescription ?: existingService?.shortDescription ?: "",
            fullDescription = request.fullDescription ?: existingService?.fullDescription ?: "",
            iconClass = request.iconClass ?: existingService?.iconClass ?: "",
            price = request.price?.toDoubleOrNull() ?: existingService?.price,
            features = request.features ?: existingService?.features ?: emptyList(),
            ctaText = request.ctaText ?: existingService?.ctaText ?: "Hire Me",
            ctaLink = request.ctaLink ?: existingService?.ctaLink ?: "/contact",
            displayOrder = request.displayOrder ?: existingService?.displayOrder ?: 0,
            featured = request.featured ?: existingService?.featured ?: false,
            createdAt = existingService?.createdAt ?: Clock.System.now().toString(),
            updatedAt = Clock.System.now().toString(),
            detailsLink = request.detailsLink ?: existingService?.detailsLink ?: ""
        )
    }
}
