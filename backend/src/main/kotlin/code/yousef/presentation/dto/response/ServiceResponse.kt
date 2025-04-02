package code.yousef.presentation.dto.response

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ServiceResponse(
    val id: Uuid?,
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
