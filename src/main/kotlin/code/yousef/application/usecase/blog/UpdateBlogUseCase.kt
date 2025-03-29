package code.yousef.application.usecase.blog

import code.yousef.application.service.BlogService
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class UpdateBlogUseCase @Inject constructor(
    private val blogService: BlogService
) {
    suspend fun execute(id: UUID, request: CreateUpdateBlogRequest): BlogResponse? {
        val blog = blogService.updateBlog(id, request) ?: return null
        return blogService.toResponse(blog)
    }

    suspend fun togglePublished(id: UUID): BlogResponse? {
        val blog = blogService.togglePublished(id) ?: return null
        return blogService.toResponse(blog)
    }
}