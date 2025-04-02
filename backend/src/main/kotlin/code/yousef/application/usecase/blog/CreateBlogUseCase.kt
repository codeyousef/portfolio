package code.yousef.application.usecase.blog

import code.yousef.application.service.BlogService
import code.yousef.presentation.dto.request.CreateUpdateBlogRequest
import code.yousef.presentation.dto.response.BlogResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class CreateBlogUseCase @Inject constructor(
    private val blogService: BlogService
) {
    suspend fun execute(request: CreateUpdateBlogRequest): BlogResponse {
        val blog = blogService.createBlog(request)
        return blogService.toResponse(blog)
    }
}