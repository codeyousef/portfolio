package code.yousef.presentation.dto.response

import java.util.*


data class ServiceResponse(
    val id: UUID?,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val iconClass: String,
    val price: String?,
    val features: List<String>,
    val ctaText: String,
    val ctaLink: String,
    val displayOrder: Int,
    val featured: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val detailsLink: String
)
