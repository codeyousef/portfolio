package code.yousef.application.service

import code.yousef.infrastructure.persistence.entity.BlogPost
import code.yousef.domain.repository.BlogPostRepo
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class BlogService {
    
    @Inject
    lateinit var blogPostRepo: BlogPostRepo
    
    fun getAllBlogPosts(): Uni<List<BlogPost>> {
        return blogPostRepo.listAll()
    }
    
    fun getPublishedBlogPosts(page: Int, size: Int): Uni<List<BlogPost>> {
        return blogPostRepo.findPublishedPosts(page, size)
    }
    
    fun getBlogPostById(id: Long): Uni<BlogPost> {
        return blogPostRepo.findById(id)
    }
    
    fun getBlogPostBySlug(slug: String): Uni<BlogPost?> {
        return blogPostRepo.findBySlug(slug)
    }
    
    fun getBlogPostsByTag(tag: String, page: Int, size: Int): Uni<List<BlogPost>> {
        return blogPostRepo.findByTag(tag, page, size)
    }
    
    fun createBlogPost(blogPost: BlogPost): Uni<BlogPost> {
        if (blogPost.slug.isBlank()) {
            blogPost.slug = blogPostRepo.generateSlug(blogPost.title)
        }
        
        return blogPostRepo.saveBlogPost(blogPost)
    }
    
    fun updateBlogPost(id: Long, updatedBlogPost: BlogPost): Uni<BlogPost> {
        return blogPostRepo.findById(id)
            .onItem().ifNotNull().transformToUni { existingPost ->
                existingPost.title = updatedBlogPost.title
                existingPost.summary = updatedBlogPost.summary
                existingPost.content = updatedBlogPost.content
                existingPost.imageUrl = updatedBlogPost.imageUrl
                existingPost.tags = updatedBlogPost.tags
                existingPost.published = updatedBlogPost.published
                
                if (existingPost.slug != updatedBlogPost.slug && updatedBlogPost.slug.isNotBlank()) {
                    existingPost.slug = updatedBlogPost.slug
                } else if (existingPost.title != updatedBlogPost.title) {
                    // Generate new slug if title changed
                    existingPost.slug = blogPostRepo.generateSlug(updatedBlogPost.title)
                }
                
                blogPostRepo.saveBlogPost(existingPost)
            }
    }
    
    fun deleteBlogPost(id: Long): Uni<Boolean> {
        return blogPostRepo.deleteById(id)
    }
}
