package code.yousef.model

import java.time.LocalDateTime

data class Service(
    val id: String = "",
    val title: String,
    val shortDescription: String = "",
    val fullDescription: String = "",
    val iconClass: String = "",
    val price: Double? = null,
    val features: List<String> = emptyList(),
    val ctaText: String = "Learn More",
    val ctaLink: String = "",
    val displayOrder: Int = 999,
    val featured: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val detailsLink: String = ""
) 