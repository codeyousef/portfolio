package code.yousef.domain.model

import java.time.LocalDateTime
import java.util.UUID

class Project(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val imageUrl: String,
    val modelUrl: String,
    val technologies: List<String>,
    val githubUrl: String,
    val demoUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val featured: Boolean = false
) {
    fun isFeatured(): Boolean = featured

    fun getTechnologyCount(): Int = technologies.size

    fun hasLiveDemo(): Boolean = demoUrl.isNotBlank()

    fun hasSourceCode(): Boolean = githubUrl.isNotBlank()

    fun getProjectAge(): Long {
        return java.time.Duration.between(createdAt, LocalDateTime.now()).toDays()
    }

    fun isRecentlyUpdated(): Boolean {
        val oneMonthAgo = LocalDateTime.now().minusMonths(1)
        return updatedAt.isAfter(oneMonthAgo)
    }

    fun withUpdatedFields(
        title: String = this.title,
        description: String = this.description,
        imageUrl: String = this.imageUrl,
        modelUrl: String = this.modelUrl,
        technologies: List<String> = this.technologies,
        githubUrl: String = this.githubUrl,
        demoUrl: String = this.demoUrl,
        featured: Boolean = this.featured
    ): Project {
        return Project(
            id = this.id,
            title = title,
            description = description,
            imageUrl = imageUrl,
            modelUrl = modelUrl,
            technologies = technologies,
            githubUrl = githubUrl,
            demoUrl = demoUrl,
            createdAt = this.createdAt,
            updatedAt = LocalDateTime.now(),
            featured = featured
        )
    }
}