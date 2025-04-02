package code.yousef.domain.model

import java.time.LocalDateTime
import java.util.*

class Service(
    val id: UUID? = null,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val iconClass: String,
    val price: String? = null,
    val features: List<String> = listOf(),
    val ctaText: String = "Hire Me",
    val ctaLink: String = "/contact",
    val displayOrder: Int = 0,
    val featured: Boolean = false,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val detailsLink: String
) {
    fun withUpdatedFields(
        title: String = this.title,
        shortDescription: String = this.shortDescription,
        fullDescription: String = this.fullDescription,
        iconClass: String = this.iconClass,
        price: String? = this.price,
        features: List<String> = this.features,
        ctaText: String = this.ctaText,
        ctaLink: String = this.ctaLink,
        displayOrder: Int = this.displayOrder,
        featured: Boolean = this.featured,
        detailsLink: String = this.detailsLink.toString()
    ): Service {
        return Service(
            id = this.id,
            title = title,
            shortDescription = shortDescription,
            fullDescription = fullDescription,
            iconClass = iconClass,
            price = price,
            features = features,
            ctaText = ctaText,
            ctaLink = ctaLink,
            displayOrder = displayOrder,
            featured = featured,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(),
            detailsLink = detailsLink
        )
    }
}
