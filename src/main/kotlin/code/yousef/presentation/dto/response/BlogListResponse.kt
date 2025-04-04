package code.yousef.presentation.dto.response

data class BlogListResponse(
    val blogs: List<BlogResponse>,
    val totalCount: Int
)