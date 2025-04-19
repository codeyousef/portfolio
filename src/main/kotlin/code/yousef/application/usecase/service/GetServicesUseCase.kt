package code.yousef.application.usecase.service

import code.yousef.application.service.ServiceService
import code.yousef.model.Service
import code.yousef.presentation.dto.response.ServiceListResponse
import code.yousef.presentation.dto.response.ServiceResponse
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import java.util.UUID


@ApplicationScoped
class GetServicesUseCase @Inject constructor(
    private val serviceService: ServiceService
) {
    suspend fun getAllServices(): List<Service> {
        return serviceService.getAllServices()
    }

    suspend fun getFeaturedServices(): List<Service> {
        return serviceService.getFeaturedServices()
    }

    suspend fun getServiceById(id: UUID): Service? {
        return serviceService.getServiceById(id)
    }

    suspend fun getServiceResponseById(id: UUID): ServiceResponse? {
        val service = serviceService.getServiceById(id) ?: return null
        return serviceService.toResponse(service)
    }

    suspend fun getAllServicesResponse(): ServiceListResponse {
        val services = serviceService.getAllServices()
        val serviceResponses = services.map {
            serviceService.toResponse(it)
        }
        return ServiceListResponse(serviceResponses, services.size)
    }

    suspend fun getOrderedServicesResponse(): ServiceListResponse {
        val services = serviceService.getServicesByDisplayOrder()
        val serviceResponses = services.map { service ->
            serviceService.toResponse(service)
        }

        return ServiceListResponse(serviceResponses, services.size)
    }
}
