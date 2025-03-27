package code.yousef.repository

import code.yousef.model.BlogPost
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import io.smallrye.mutiny.Uni
import java.time.LocalDateTime
import jakarta.enterprise.context.ApplicationScoped
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.*

@ApplicationScoped
class BlogPostRepository(private val sessionFactory: SessionFactory) : PanacheRepository<BlogPost> {
    
    fun findPublishedPosts(page: Int, size: Int): Uni<List<BlogPost>> {
        return sessionFactory.withSession {
            find("published = true ORDER BY publishDate DESC")
                .page(page, size)
                .list()
        }
    }
    
    fun findBySlug(slug: String): Uni<BlogPost?> {
        return sessionFactory.withSession { find("slug = ?1 AND published = true", slug).firstResult() }
    }
    
    fun findByTag(tag: String, page: Int, size: Int): Uni<List<BlogPost>> {
        return sessionFactory.withSession {
            find("published = true AND ?1 MEMBER OF tags ORDER BY publishDate DESC", tag)
                .page(page, size)
                .list()

        }
    }
    
    fun saveBlogPost(blogPost: BlogPost): Uni<BlogPost> {
        val now = LocalDateTime.now()
        
        if (blogPost.id == null) {
            blogPost.createdAt = now
            
            if (blogPost.published) {
                blogPost.publishDate = now
            }
        } else if (!blogPost.published) {
            blogPost.publishDate = now
        }
        
        blogPost.updatedAt = now

        return sessionFactory.withSession { persistAndFlush(blogPost) }
    }
    
    fun generateSlug(title: String): String {
        return title.lowercase(Locale.getDefault())
            .replace("[^a-z0-9\\s-]".toRegex(), "")
            .replace("\\s+".toRegex(), "-")
            .replace("-+".toRegex(), "-")
    }
}
