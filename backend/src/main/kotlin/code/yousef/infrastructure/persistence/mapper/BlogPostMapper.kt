package code.yousef.infrastructure.persistence.mapper

import code.yousef.repository.BlogRepo
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.model.BlogPost
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.Locale
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class BlogPostMapper {
    fun toDomain(entity: BlogPostEntity): BlogPost {
        return BlogPost(
            id = entity.id.toString(),
            title = entity.title,
            summary = entity.summary,
            content = entity.content,
            imageUrl = entity.imageUrl,
            tags = entity.tags,
            publishDate = entity.publishDate.toString(),
            createdAt = entity.createdAt.toString(),
            updatedAt = entity.updatedAt.toString(),
            published = entity.published,
            slug = entity.slug
        )
    }

    fun toEntity(blog: BlogPost): BlogPostEntity {
        val entity = BlogPostEntity()
        entity.id = blog.id?.let { UUID.fromString(it) }
        entity.title = blog.title
        entity.summary = blog.summary
        entity.content = blog.content
        entity.imageUrl = blog.imageUrl ?: ""
        entity.tags = blog.tags
        
        val now = Clock.System.now()
        entity.publishDate = blog.publishDate?.let { Instant.parse(it) } ?: now
        entity.createdAt = blog.createdAt?.let { Instant.parse(it) } ?: now
        entity.updatedAt = blog.updatedAt?.let { Instant.parse(it) } ?: now
        
        entity.published = blog.published
        entity.slug = blog.slug
        return entity
    }

    fun updateEntity(entity: BlogPostEntity, blog: BlogPost): BlogPostEntity {
        entity.title = blog.title
        entity.summary = blog.summary
        entity.content = blog.content
        entity.imageUrl = blog.imageUrl ?: ""
        entity.tags = blog.tags
        entity.publishDate = blog.publishDate?.let { Instant.parse(it) } ?: entity.publishDate
        entity.updatedAt = Clock.System.now()
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
        val clock: Clock = Clock.System
        val now = clock.now()
        val nowStr = now.toString()

        val slug = if (request.slug.isNullOrBlank()) {
            existingBlog?.slug ?: generateSlug(request.title ?: existingBlog?.title ?: "") ?: ""
        } else {
            request.slug
        }

        return BlogPost(
            id = existingBlog?.id,
            title = request.title ?: existingBlog?.title ?: "",
            summary = request.summary ?: existingBlog?.summary ?: "",
            content = request.content ?: existingBlog?.content ?: "",
            imageUrl = request.imageUrl ?: existingBlog?.imageUrl ?: "",
            tags = request.tags ?: existingBlog?.tags ?: emptyList(),
            publishDate = if (request.published == true && existingBlog?.published != true) nowStr else existingBlog?.publishDate,
            createdAt = existingBlog?.createdAt ?: nowStr,
            updatedAt = nowStr,
            published = request.published ?: existingBlog?.published ?: false,
            slug = slug
        )
    }

    fun toResponse(blog: BlogPost): BlogResponse {
        val uuid = blog.id?.let { 
            try {
                Uuid.parse(it)
            } catch (e: Exception) {
                Uuid.parse("00000000-0000-0000-0000-000000000000")
            }
        } ?: Uuid.parse("00000000-0000-0000-0000-000000000000")
        
        return BlogResponse(
            id = uuid,
            title = blog.title,
            summary = blog.summary,
            content = blog.content,
            imageUrl = blog.imageUrl ?: "",
            tags = blog.tags,
            publishDate = blog.publishDate ?: "",
            createdAt = blog.createdAt ?: "",
            updatedAt = blog.updatedAt ?: "",
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
            publishDate = response.publishDate,
            createdAt = response.createdAt,
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
        entity.id = response.id?.let { UUID.fromString(it.toString()) }
        entity.title = response.title
        entity.summary = response.summary
        entity.content = response.content
        entity.imageUrl = response.imageUrl
        entity.tags = response.tags
        
        val now = Clock.System.now()
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
            Clock.System.now()
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