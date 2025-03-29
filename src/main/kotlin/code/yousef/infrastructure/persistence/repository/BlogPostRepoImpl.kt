package code.yousef.infrastructure.persistence.repository

import code.yousef.domain.model.BlogPost
import code.yousef.domain.repository.BlogRepo
import code.yousef.infrastructure.persistence.mapper.BlogPostMapper
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.*

@ApplicationScoped
class BlogRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val blogPostMapper: BlogPostMapper
) : PanacheRepositoryBase<BlogPost, UUID>, BlogRepo {

    override suspend fun findBlogById(id: UUID): BlogPost? {
        return sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
    }


    override suspend fun findBySlug(slug: String): BlogPost? {
        return sessionFactory.withSession {
            find("slug = ?1 AND published = true", slug).firstResult()
        }.awaitSuspending()
    }

    override suspend fun findPublishedBlogs(page: Int, size: Int): List<BlogPost> {
        return sessionFactory.withSession {
            find("published = true ORDER BY publishDate DESC")
                .page(page, size)
                .list()
        }.awaitSuspending()
    }

    override suspend fun findByTag(tag: String, page: Int, size: Int): List<BlogPost> {
        return sessionFactory.withSession {
            find("published = true AND ?1 MEMBER OF tags ORDER BY publishDate DESC", tag)
                .page(page, size)
                .list()
        }.awaitSuspending()
    }

    override suspend fun saveBlog(blog: BlogPost): BlogPost {
        if (blog.id != null) {
            val existingPost = sessionFactory.withSession {
                findById(blog.id)
            }.awaitSuspending()
            blogPostMapper.updateEntity(blogPostMapper.toEntity(existingPost), blog)
        } else {
            blogPostMapper.toEntity(blog)
        }

        return sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(blog)
            }
        }.awaitSuspending()
    }

    override suspend fun getAllBlogs(): List<BlogPost> {
        return sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
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