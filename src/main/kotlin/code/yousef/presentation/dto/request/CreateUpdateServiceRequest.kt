package code.yousef.presentation.dto.request

data class CreateUpdateServiceRequest(
    val title: String? = null,
    val shortDescription: String? = null,
    val fullDescription: String? = null,
    val iconClass: String? = null,
    val price: String? = null,
    val features: List<String>? = null,
    val ctaText: String? = null,
    val ctaLink: String? = null,
    val displayOrder: Int? = null,
    val featured: Boolean? = null
)
