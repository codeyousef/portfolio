package code.yousef.presentation.resource

import code.yousef.application.usecase.service.CreateServiceUseCase
import code.yousef.application.usecase.service.DeleteServiceUseCase
import code.yousef.application.usecase.service.GetServicesUseCase
import code.yousef.application.usecase.service.UpdateServiceUseCase
import code.yousef.infrastructure.template.ServiceTemplates
import code.yousef.presentation.dto.request.CreateUpdateServiceRequest
import code.yousef.presentation.dto.response.ServiceListResponse
import io.quarkus.qute.TemplateInstance
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI
import java.util.*

@Path("/services")
class ServiceResource {
    @Inject
    lateinit var serviceTemplates: ServiceTemplates

    @Inject
    lateinit var getServicesUseCase: GetServicesUseCase

    @Inject
    lateinit var createServiceUseCase: CreateServiceUseCase

    @Inject
    lateinit var updateServiceUseCase: UpdateServiceUseCase

    @Inject
    lateinit var deleteServiceUseCase: DeleteServiceUseCase

    // Public endpoints
    @GET
    @Produces(MediaType.TEXT_HTML)
    suspend fun getServicesPage(): String? {
        val services = getServicesUseCase.getOrderedServicesResponse().services
        return serviceTemplates.buildServicesPage(services).render()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    suspend fun getServiceDetailsPage(@PathParam("id") id: UUID): String? {
        val service = getServicesUseCase.getServiceResponseById(id)
        return serviceTemplates.buildServiceDetailsPage(service).render()
    }

    // Admin API endpoints
    @GET
    @Path("/api/services")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun getAllServices(): ServiceListResponse {
        return getServicesUseCase.getAllServicesResponse()
    }

    @GET
    @Path("/api/services/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun getServiceById(@PathParam("id") id: UUID): Response {
        val serviceResponse = getServicesUseCase.getServiceResponseById(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(serviceResponse).build()
    }

    @POST
    @Path("/api/services")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun createService(request: CreateUpdateServiceRequest): Response {
        val serviceResponse = createServiceUseCase.execute(request)
        return Response.created(URI.create("/api/services/${serviceResponse.id}"))
            .entity(serviceResponse)
            .build()
    }

    @PUT
    @Path("/api/services/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun updateService(
        @PathParam("id") id: UUID,
        request: CreateUpdateServiceRequest
    ): Response {
        val serviceResponse = updateServiceUseCase.execute(id, request)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(serviceResponse).build()
    }

    @PUT
    @Path("/api/services/{id}/toggle-featured")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun toggleFeatured(@PathParam("id") id: UUID): Response {
        val serviceResponse = updateServiceUseCase.toggleFeatured(id)
            ?: return Response.status(Response.Status.NOT_FOUND).build()
        return Response.ok(serviceResponse).build()
    }

    @DELETE
    @Path("/api/services/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    suspend fun deleteService(@PathParam("id") id: UUID): Response {
        val deleted = deleteServiceUseCase.execute(id)
        return if (deleted) Response.noContent().build()
        else Response.status(Response.Status.NOT_FOUND).build()
    }
}
