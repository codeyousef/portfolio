package code.yousef.application.usecase.service

import code.yousef.application.service.ServiceService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID

@ApplicationScoped
class DeleteServiceUseCase @Inject constructor(
    private val serviceService: ServiceService
) {
    suspend fun execute(id: UUID): Boolean {
        return serviceService.deleteService(id)
    }
}
