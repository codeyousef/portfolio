package code.yousef.application.usecase.service

import code.yousef.application.service.ServiceService
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject

@ApplicationScoped
class CreateServiceUseCase @Inject constructor(
    private val serviceService: ServiceService
) {
    suspend fun execute(request: CreateUpdateServiceRequest): ServiceResponse {
        val service = serviceService.createService(request)
        return  serviceService.toResponse(service)
    }
}
