package code.yousef.infrastructure.persistence.mapper

import code.yousef.domain.model.Service
import code.yousef.infrastructure.persistence.entity.ServiceEntity
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@ApplicationScoped
class ServiceMapper {
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun toDomain(entity: ServiceEntity): Service {
        return Service(
            id = entity.id,
            title = entity.title,
            shortDescription = entity.shortDescription,
            fullDescription = entity.fullDescription,
            iconClass = entity.iconClass,
            price = entity.price,
            features = entity.features,
            ctaText = entity.ctaText,
            ctaLink = entity.ctaLink,
            displayOrder = entity.displayOrder,
            featured = entity.featured,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(service: Service): ServiceEntity {
        val entity = ServiceEntity()
        entity.id = service.id
        entity.title = service.title
        entity.shortDescription = service.shortDescription
        entity.fullDescription = service.fullDescription
        entity.iconClass = service.iconClass
        entity.price = service.price
        entity.features = service.features
        entity.ctaText = service.ctaText
        entity.ctaLink = service.ctaLink
        entity.displayOrder = service.displayOrder
        entity.featured = service.featured
        entity.createdAt = service.createdAt
        entity.updatedAt = service.updatedAt
        return entity
    }

    fun updateEntity(entity: ServiceEntity, service: Service): ServiceEntity {
        entity.title = service.title
        entity.shortDescription = service.shortDescription
        entity.fullDescription = service.fullDescription
        entity.iconClass = service.iconClass
        entity.price = service.price
        entity.features = service.features
        entity.ctaText = service.ctaText
        entity.ctaLink = service.ctaLink
        entity.displayOrder = service.displayOrder
        entity.featured = service.featured
        entity.updatedAt = LocalDateTime.now()
        return entity
    }

    fun toDomain(request: CreateUpdateServiceRequest, existingService: Service? = null): Service {
        val now = LocalDateTime.now()
        return Service(
            id = existingService?.id,
            title = request.title ?: existingService?.title ?: "",
            shortDescription = request.shortDescription ?: existingService?.shortDescription ?: "",
            fullDescription = request.fullDescription ?: existingService?.fullDescription ?: "",
            iconClass = request.iconClass ?: existingService?.iconClass ?: "",
            price = request.price ?: existingService?.price,
            features = request.features ?: existingService?.features ?: emptyList(),
            ctaText = request.ctaText ?: existingService?.ctaText ?: "Hire Me",
            ctaLink = request.ctaLink ?: existingService?.ctaLink ?: "/contact",
            displayOrder = request.displayOrder ?: existingService?.displayOrder ?: 0,
            featured = request.featured ?: existingService?.featured ?: false,
            createdAt = existingService?.createdAt ?: now,
            updatedAt = now
        )
    }

    fun toResponse(service: Service): ServiceResponse {
        return ServiceResponse(
            id = service.id ?: UUID.randomUUID(),
            title = service.title,
            shortDescription = service.shortDescription,
            fullDescription = service.fullDescription,
            iconClass = service.iconClass,
            price = service.price,
            features = service.features,
            ctaText = service.ctaText,
            ctaLink = service.ctaLink,
            displayOrder = service.displayOrder,
            featured = service.featured,
            createdAt = service.createdAt.format(dateFormatter),
            updatedAt = service.updatedAt.format(dateFormatter)
        )
    }
}
