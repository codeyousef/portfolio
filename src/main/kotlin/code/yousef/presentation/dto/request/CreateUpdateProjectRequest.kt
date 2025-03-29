package code.yousef.presentation.dto.request

data class CreateUpdateProjectRequest(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val modelUrl: String? = null,
    val technologies: List<String>? = null,
    val githubUrl: String? = null,
    val demoUrl: String? = null,
    val featured: Boolean? = null
)