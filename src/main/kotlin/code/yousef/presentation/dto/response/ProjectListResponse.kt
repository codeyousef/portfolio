package code.yousef.presentation.dto.response

data class ProjectListResponse(
    val projects: List<ProjectResponse>,
    val totalCount: Int
)