package code.yousef.model

import kotlinx.datetime.Clock

data class BlogPost(
    val id: String? = null,
    val title: String,
    val summary: String,
    val content: String,
    val imageUrl: String? = null,
    val tags: List<String> = emptyList(),
    val publishDate: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val published: Boolean = false,
    val slug: String,
    val authorName: String? = null,
    val readingTimeMinutes: Int? = null
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
        imageUrl: String = this.imageUrl.toString(),
        tags: List<String> = this.tags,
        published: Boolean = this.published,
        slug: String = this.slug
    ): BlogPost {
        val clock: Clock = Clock.System
        val now = clock.now()
        val newPublishDate = if (!this.published && published) now else this.publishDate

        return BlogPost(
            id = this.id,
            title = title,
            summary = summary,
            content = content,
            imageUrl = imageUrl,
            tags = tags,
            publishDate = newPublishDate?.toString()?.replace("T", " ")?.substringBefore("."),
            createdAt = this.createdAt?.replace("T", " ")?.substringBefore("."),
            updatedAt = now.toString().replace("T", " ").substringBefore("."),
            published = published,
            slug = slug
        )
    }
}