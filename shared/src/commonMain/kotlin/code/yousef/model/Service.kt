package code.yousef.model

data class Service(
    val id: String,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val iconClass: String = "",
    val price: Double? = null,
    val features: List<String> = emptyList(),
    val ctaText: String = "Hire Me",
    val ctaLink: String = "/contact",
    val displayOrder: Int = 0,
    val featured: Boolean = false,
    val createdAt: String = "",
    val updatedAt: String = "",
    val detailsLink: String = ""
) 