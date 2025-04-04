package code.yousef.model

import java.time.LocalDateTime

data class Project(
    val id: String = "",
    val title: String,
    val description: String = "",
    val imageUrl: String = "",
    val modelUrl: String = "",
    val technologies: List<String> = emptyList(),
    val githubUrl: String = "",
    val demoUrl: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val featured: Boolean = false
) 