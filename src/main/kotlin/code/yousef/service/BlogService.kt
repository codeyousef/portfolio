package code.yousef.service

import code.yousef.model.BlogPost
import code.yousef.repository.BlogPostRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class BlogService {
    
    @Inject
    lateinit var blogPostRepository: BlogPostRepository
    
    fun getAllBlogPosts(): Uni<List<BlogPost>> {
        return blogPostRepository.listAll()
    }
    
    fun getPublishedBlogPosts(page: Int, size: Int): Uni<List<BlogPost>> {
        return blogPostRepository.findPublishedPosts(page, size)
    }
    
    fun getBlogPostById(id: Long): Uni<BlogPost> {
        return blogPostRepository.findById(id)
    }
    
    fun getBlogPostBySlug(slug: String): Uni<BlogPost?> {
        return blogPostRepository.findBySlug(slug)
    }
    
    fun getBlogPostsByTag(tag: String, page: Int, size: Int): Uni<List<BlogPost>> {
        return blogPostRepository.findByTag(tag, page, size)
    }
    
    fun createBlogPost(blogPost: BlogPost): Uni<BlogPost> {
        if (blogPost.slug.isBlank()) {
            blogPost.slug = blogPostRepository.generateSlug(blogPost.title)
        }
        
        return blogPostRepository.saveBlogPost(blogPost)
    }
    
    fun updateBlogPost(id: Long, updatedBlogPost: BlogPost): Uni<BlogPost> {
        return blogPostRepository.findById(id)
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
                    existingPost.slug = blogPostRepository.generateSlug(updatedBlogPost.title)
                }
                
                blogPostRepository.saveBlogPost(existingPost)
            }
    }
    
    fun deleteBlogPost(id: Long): Uni<Boolean> {
        return blogPostRepository.deleteById(id)
    }
}
