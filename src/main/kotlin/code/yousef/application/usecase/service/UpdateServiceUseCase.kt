package code.yousef.application.usecase.service

import code.yousef.application.service.ServiceService
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class UpdateServiceUseCase @Inject constructor(
    private val serviceService: ServiceService
) {
    suspend fun execute(id: UUID, request: CreateUpdateServiceRequest): ServiceResponse? {
        val service = serviceService.updateService(id, request) ?: return null
        return serviceService.toResponse(service)
    }

    suspend fun toggleFeatured(id: UUID): ServiceResponse? {
        val service = serviceService.toggleFeatured(id) ?: return null
        return serviceService.toResponse(service)
    }
}
