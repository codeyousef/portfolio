package code.yousef.infrastructure.persistence.mapper

import code.yousef.domain.model.BlogPost
import code.yousef.domain.repository.BlogRepo
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@ApplicationScoped
class BlogPostMapper {
    private val dateFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun toDomain(entity: BlogPostEntity): BlogPost {
        return BlogPost(
            id = entity.id,
            title = entity.title,
            summary = entity.summary,
            content = entity.content,
            imageUrl = entity.imageUrl,
            tags = entity.tags,
            publishDate = entity.publishDate,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            published = entity.published,
            slug = entity.slug
        )
    }

    fun toEntity(blog: BlogPost): BlogPostEntity {
        val entity = BlogPostEntity()
        entity.id = blog.id
        entity.title = blog.title
        entity.summary = blog.summary
        entity.content = blog.content
        entity.imageUrl = blog.imageUrl
        entity.tags = blog.tags
        entity.publishDate = blog.publishDate
        entity.createdAt = blog.createdAt
        entity.updatedAt = blog.updatedAt
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
        entity.publishDate = blog.publishDate
        entity.updatedAt = LocalDateTime.now()
        entity.published = blog.published
        entity.slug = blog.slug
        return entity
    }

    fun toDomain(
        request: CreateUpdateBlogRequest,
        existingBlog: BlogPost? = null,
        blogRepo: BlogRepo? = null
    ): BlogPost {
        val now = LocalDateTime.now()

        val slug = if (request.slug.isNullOrBlank()) {
            existingBlog?.slug ?: blogRepo?.generateSlug(request.title ?: existingBlog?.title ?: "") ?: ""
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
            publishDate = if (request.published == true && existingBlog?.published != true) now else existingBlog?.publishDate
                ?: now,
            createdAt = existingBlog?.createdAt ?: now,
            updatedAt = now,
            published = request.published ?: existingBlog?.published ?: false,
            slug = slug
        )
    }

    fun toResponse(blog: BlogPost): BlogResponse {
        return BlogResponse(
            id = blog.id ?: UUID.randomUUID(),
            title = blog.title,
            summary = blog.summary,
            content = blog.content,
            imageUrl = blog.imageUrl,
            tags = blog.tags,
            publishDate = blog.publishDate.format(dateFormatter),
            createdAt = blog.createdAt.format(dateFormatter),
            updatedAt = blog.updatedAt.format(dateFormatter),
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
            id = response.id,
            title = response.title,
            summary = response.summary,
            content = response.content,
            imageUrl = response.imageUrl,
            tags = response.tags,
            publishDate = LocalDateTime.parse(response.publishDate, dateFormatter),
            createdAt = LocalDateTime.parse(response.createdAt, dateFormatter),
            updatedAt = LocalDateTime.parse(response.updatedAt, dateFormatter),
            published = response.published,
            slug = response.slug
        )
    }

    /**
     * Convert BlogResponse to BlogPostEntity
     */
    fun toEntityFromResponse(response: BlogResponse): BlogPostEntity {
        val entity = BlogPostEntity()
        entity.id = response.id
        entity.title = response.title
        entity.summary = response.summary
        entity.content = response.content
        entity.imageUrl = response.imageUrl
        entity.tags = response.tags
        entity.publishDate = LocalDateTime.parse(response.publishDate, dateFormatter)
        entity.createdAt = LocalDateTime.parse(response.createdAt, dateFormatter)
        entity.updatedAt = LocalDateTime.parse(response.updatedAt, dateFormatter)
        entity.published = response.published
        entity.slug = response.slug
        return entity
    }
}