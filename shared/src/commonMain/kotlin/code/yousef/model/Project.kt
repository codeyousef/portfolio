package code.yousef.model

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String = "",
    val modelUrl: String = "",
    val technologies: List<String> = emptyList(),
    val githubUrl: String = "",
    val demoUrl: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val featured: Boolean = false
)