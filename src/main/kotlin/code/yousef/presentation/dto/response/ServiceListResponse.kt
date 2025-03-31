package code.yousef.presentation.dto.response

data class ServiceListResponse(
    val services: List<ServiceResponse>,
    val totalCount: Int
)
