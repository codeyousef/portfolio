package code.yousef.infrastructure.persistence.repository

import code.yousef.model.BlogPost
import code.yousef.repository.BlogRepo
import code.yousef.infrastructure.persistence.entity.BlogPostEntity
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.*
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import java.util.UUID

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class BlogRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val blogPostMapper: BlogPostMapper
) : PanacheRepositoryBase<BlogPostEntity, UUID>, BlogRepo {

    private fun Uuid.toJavaUUID(): UUID {
        return UUID.fromString(this.toString())
    }

    override suspend fun findBlogById(id: UUID): BlogPost? {
        val entity = sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
        return entity?.let { blogPostMapper.toDomain(it) }
    }

    override suspend fun findBySlug(slug: String): BlogPost? {
        val entity = sessionFactory.withSession {
            find("slug = ?1 AND published = true", slug).firstResult()
        }.awaitSuspending()
        return entity?.let { blogPostMapper.toDomain(it) }
    }

    override suspend fun findPublishedBlogs(page: Int, size: Int): List<BlogPost> {
        val entities = sessionFactory.withSession {
            find("published = true ORDER BY publishDate DESC")
                .page(page, size)
                .list()
        }.awaitSuspending()
        return entities.map { blogPostMapper.toDomain(it) }
    }

    override suspend fun findByTag(tag: String, page: Int, size: Int): List<BlogPost> {
        val entities = sessionFactory.withSession {
            find("published = true AND ?1 MEMBER OF tags ORDER BY publishDate DESC", tag)
                .page(page, size)
                .list()
        }.awaitSuspending()
        return entities.map { blogPostMapper.toDomain(it) }
    }

    override suspend fun saveBlog(blog: BlogPost): BlogPost {
        val entity = if (blog.id != null && blog.id.toString().isNotEmpty()) {
            try {
                val idStr = blog.id.toString()
                val javaUuid = UUID.fromString(idStr)
                val existingEntity = sessionFactory.withSession {
                    findById(javaUuid)
                }.awaitSuspending()

                if (existingEntity != null) {
                    blogPostMapper.updateEntity(existingEntity, blog)
                } else {
                    blogPostMapper.toEntity(blog)
                }
            } catch (e: Exception) {
                // If id is invalid, just create a new entity
                blogPostMapper.toEntity(blog)
            }
        } else {
            blogPostMapper.toEntity(blog)
        }

        val savedEntity = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(entity)
            }
        }.awaitSuspending()

        return blogPostMapper.toDomain(savedEntity)
    }

    override suspend fun getAllBlogs(): List<BlogPost> {
        val entities = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        return entities.map { blogPostMapper.toDomain(it) }
    }

    override suspend fun deleteBlog(id: UUID): Boolean {
        return sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
    }

    override fun generateSlug(title: String): String {
        return title.lowercase(Locale.getDefault())
            .replace("[^a-z0-9\\s-]".toRegex(), "")
            .replace("\\s+".toRegex(), "-")
            .replace("-+".toRegex(), "-")
    }
}