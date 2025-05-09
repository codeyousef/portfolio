package code.yousef.application.usecase.blog

import code.yousef.application.service.BlogService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class DeleteBlogUseCase @Inject constructor(
    private val blogService: BlogService
) {
    suspend fun execute(id: UUID): Boolean {
        return blogService.deleteBlog(id)
    }
}