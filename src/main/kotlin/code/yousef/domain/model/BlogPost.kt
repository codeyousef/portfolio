package code.yousef.domain.model

import java.time.LocalDateTime
import java.util.*

class BlogPost(
    val id: UUID? = null,
    val title: String,
    val summary: String,
    val content: String,
    val imageUrl: String,
    val tags: List<String>,
    val publishDate: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val published: Boolean = false,
    val slug: String
) {
    fun isPublished(): Boolean = published

    fun isDraft(): Boolean = !published

    fun hasTag(tag: String): Boolean = tags.contains(tag)

    fun getReadingTime(): Int {
        val wordCount = content.split("\\s+".toRegex()).size
        return (wordCount / 200) + 1 // Minimum 1 minute
    }

    fun withUpdatedFields(
        title: String = this.title,
        summary: String = this.summary,
        content: String = this.content,
        imageUrl: String = this.imageUrl,
        tags: List<String> = this.tags,
        published: Boolean = this.published,
        slug: String = this.slug
    ): BlogPost {
        val now = LocalDateTime.now()
        val newPublishDate = if (!this.published && published) now else this.publishDate

        return BlogPost(
            id = this.id,
            title = title,
            summary = summary,
            content = content,
            imageUrl = imageUrl,
            tags = tags,
            publishDate = newPublishDate,
            createdAt = this.createdAt,
            updatedAt = now,
            published = published,
            slug = slug
        )
    }
}