package code.yousef.presentation.dto.request

data class CreateUpdateBlogRequest(
    val title: String? = null,
    val summary: String? = null,
    val content: String? = null,
    val imageUrl: String? = null,
    val tags: List<String>? = null,
    val published: Boolean? = null,
    val slug: String? = null
)