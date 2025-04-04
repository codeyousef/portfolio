package code.yousef.model

import java.time.LocalDateTime
import kotlin.math.ceil

data class BlogPost(
    val id: String = "",
    val title: String,
    val summary: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val tags: List<String> = emptyList(),
    val publishDate: LocalDateTime? = null,
    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: String = LocalDateTime.now().toString(),
    val published: Boolean = false,
    val slug: String = "",
    val authorName: String = "",
    val readingTimeMinutes: Int? = null
) {
    fun isPublished() = published && publishDate != null && publishDate <= LocalDateTime.now()
    
    fun isDraft() = !isPublished()
    
    fun hasTag(tag: String): Boolean = tags.contains(tag.trim())
    
    /**
     * Calculate reading time based on word count
     * Average reading speed is ~200-250 words per minute
     */
    fun getReadingTime(): Int {
        if (readingTimeMinutes != null) {
            return readingTimeMinutes
        }
        
        val wordCount = content.split(Regex("\\s+")).size
        val minutesRaw = wordCount / 200.0
        
        // Round up to nearest minute, with a minimum of 1 minute
        return maxOf(1, ceil(minutesRaw).toInt())
    }
    
    /**
     * Creates a new instance with updated fields, properly managing timestamps
     */
    fun withUpdatedFields(
        title: String = this.title,
        summary: String = this.summary,
        content: String = this.content,
        imageUrl: String = this.imageUrl,
        tags: List<String> = this.tags,
        published: Boolean = this.published,
        slug: String = this.slug,
        authorName: String = this.authorName,
        readingTimeMinutes: Int? = this.readingTimeMinutes
    ): BlogPost {
        val now = LocalDateTime.now()
        val newPublishDate = if (published && !this.published) now else this.publishDate
        
        return copy(
            title = title,
            summary = summary,
            content = content,
            imageUrl = imageUrl,
            tags = tags,
            publishDate = newPublishDate,
            updatedAt = now.toString(),
            published = published,
            slug = slug,
            authorName = authorName,
            readingTimeMinutes = readingTimeMinutes
        )
    }
} 