package code.yousef.presentation.dto.response

import java.util.*

data class ProjectResponse(
    val id: UUID?,
    val title: String,
    val description: String,
    val imageUrl: String,
    val modelUrl: String,
    val technologies: List<String>,
    val githubUrl: String,
    val demoUrl: String,
    val featured: Boolean,
    val createdAt: String,
    val updatedAt: String
)