package code.yousef.application.usecase.service

import code.yousef.application.service.ServiceService
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@ApplicationScoped
class DeleteServiceUseCase @Inject constructor(
    private val serviceService: ServiceService
) {
    suspend fun execute(id: Uuid): Boolean {
        return serviceService.deleteService(id)
    }
}
