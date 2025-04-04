package code.yousef.infrastructure.persistence.mapper

import code.yousef.infrastructure.persistence.converter.InstantAttributeConverter
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.model.BlogPost
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import code.yousef.repository.BlogRepo
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class BlogPostMapper {

    val converter = InstantAttributeConverter()
    val now: Instant = Instant.now()

    fun toDomain(entity: BlogPostEntity): BlogPost {
        return BlogPost(
            id = entity.id.toString(),
            title = entity.title,
            summary = entity.summary,
            content = entity.content,
            imageUrl = entity.imageUrl,
            tags = entity.tags,
            publishDate = converter.convertToDatabaseColumn(entity.publishDate),
            createdAt = converter.convertToDatabaseColumn(entity.createdAt),
            updatedAt = entity.updatedAt.toString(),
            published = entity.published,
            slug = entity.slug
        )
    }

    fun toEntity(blog: BlogPost): BlogPostEntity {
        val entity = BlogPostEntity()
        entity.id = blog.id.let { UUID.fromString(it) }
        entity.title = blog.title
        entity.summary = blog.summary
        entity.content = blog.content
        entity.imageUrl = blog.imageUrl
        entity.tags = blog.tags

        val now = LocalDateTime.now()
        entity.publishDate = (blog.publishDate?.let { converter.convertToEntityAttribute(it) } ?: now) as Instant
        entity.createdAt = (blog.createdAt?.let { converter.convertToEntityAttribute(it) } ?: now) as Instant
        entity.updatedAt = Instant.parse(blog.updatedAt)

        entity.published = blog.published
        entity.slug = blog.slug
        return entity
    }

    fun updateEntity(entity: BlogPostEntity, blog: BlogPost): BlogPostEntity {
        entity.title = blog.title
        entity.summary = blog.summary
        entity.content = blog.content
        entity.imageUrl = blog.imageUrl
        entity.tags = blog.tags
        entity.publishDate = blog.publishDate?.let { converter.convertToEntityAttribute(it) } ?: entity.publishDate
        entity.updatedAt = now
        entity.published = blog.published
        entity.slug = blog.slug
        return entity
    }

    /**
     * Maps CreateUpdateBlogRequest to BlogPost, using an existing blog as a base if provided.
     * This is for updating an existing blog post.
     */
    fun toDomain(
        request: CreateUpdateBlogRequest,
        existingBlog: BlogPost? = null,
        blogRepo: BlogRepo? = null
    ): BlogPost {
        val nowStr = now.toString()

        val slug = if (request.slug.isNullOrBlank()) {
            existingBlog?.slug ?: generateSlug(request.title ?: existingBlog?.title ?: "")
        } else {
            request.slug
        }

        return BlogPost(
            id = existingBlog?.id.toString(),
            title = request.title ?: existingBlog?.title ?: "",
            summary = request.summary ?: existingBlog?.summary ?: "",
            content = request.content ?: existingBlog?.content ?: "",
            imageUrl = request.imageUrl ?: existingBlog?.imageUrl ?: "",
            tags = request.tags ?: existingBlog?.tags ?: emptyList(),
            publishDate = (if (request.published == true && existingBlog?.published != true) nowStr else existingBlog?.publishDate) as LocalDateTime?,
            createdAt = (existingBlog?.createdAt ?: nowStr) as LocalDateTime?,
            updatedAt = nowStr,
            published = (request.published ?: existingBlog?.published) == true,
            slug = slug
        )
    }

    fun toResponse(blog: BlogPost): BlogResponse {

        return BlogResponse(
            id = UUID.fromString(blog.id),
            title = blog.title,
            summary = blog.summary,
            content = blog.content,
            imageUrl = blog.imageUrl,
            tags = blog.tags,
            publishDate = (blog.publishDate ?: "").toString(),
            createdAt = (blog.createdAt ?: "").toString(),
            updatedAt = blog.updatedAt,
            published = blog.published,
            slug = blog.slug,
            readingTime = blog.getReadingTime()
        )
    }

    /**
     * Convert BlogResponse to BlogPost domain model
     */
    fun toDomainFromResponse(response: BlogResponse): BlogPost {
        return BlogPost(
            id = response.id.toString(),
            title = response.title,
            summary = response.summary,
            content = response.content,
            imageUrl = response.imageUrl,
            tags = response.tags,
            publishDate = LocalDateTime.parse(response.publishDate),
            createdAt = LocalDateTime.parse(response.createdAt),
            updatedAt = response.updatedAt,
            published = response.published,
            slug = response.slug
        )
    }

    /**
     * Convert BlogResponse to BlogPostEntity
     */
    fun toEntityFromResponse(response: BlogResponse): BlogPostEntity {
        val entity = BlogPostEntity()
        entity.id = response.id.let { UUID.fromString(it.toString()) }
        entity.title = response.title
        entity.summary = response.summary
        entity.content = response.content
        entity.imageUrl = response.imageUrl
        entity.tags = response.tags

        entity.publishDate = parseInstantOrNow(response.publishDate)
        entity.createdAt = parseInstantOrNow(response.createdAt)
        entity.updatedAt = parseInstantOrNow(response.updatedAt)

        entity.published = response.published
        entity.slug = response.slug
        return entity
    }

    private fun parseInstantOrNow(dateStr: String): Instant {
        return try {
            Instant.parse(dateStr)
        } catch (e: Exception) {
            now
        }
    }

    /**
     * Generate a URL-friendly slug from a title
     */
    fun generateSlug(title: String): String {
        return title.lowercase(Locale.getDefault())
            .replace("[^a-z0-9\\s-]".toRegex(), "")
            .replace("\\s+".toRegex(), "-")
            .replace("-+".toRegex(), "-")
    }
}