package code.yousef.infrastructure.persistence.repository

import code.yousef.domain.model.Service
import code.yousef.domain.repository.ServiceRepo
import code.yousef.infrastructure.persistence.entity.ServiceEntity
import code.yousef.infrastructure.persistence.mapper.ServiceMapper
import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepositoryBase
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import java.util.*

@ApplicationScoped
class ServiceRepoImpl @Inject constructor(
    private val sessionFactory: SessionFactory,
    private val serviceMapper: ServiceMapper
) : PanacheRepositoryBase<ServiceEntity, UUID>, ServiceRepo {

    override suspend fun findServiceById(id: UUID): Service? {
        val service = sessionFactory.withSession {
            findById(id)
        }.awaitSuspending()
        return serviceMapper.toDomain(service)
    }

    override suspend fun findFeaturedServices(): List<Service> {
        val services = sessionFactory.withSession {
            find("featured = true ORDER BY displayOrder ASC").list()
        }.awaitSuspending()
        return services.map { service -> serviceMapper.toDomain(service) }
    }

    override suspend fun saveService(service: Service): Service {
        val entity = serviceMapper.toEntity(service)
        val savedService = sessionFactory.withSession { session ->
            session.withTransaction {
                persistAndFlush(entity)
            }
        }.awaitSuspending()
        return serviceMapper.toDomain(savedService)
    }

    override suspend fun updateService(service: Service): Service {
        if (service.id == null) {
            throw IllegalArgumentException("Cannot update a service without an ID")
        }

        val existingEntity = sessionFactory.withSession {
            findById(service.id)
        }.awaitSuspending() ?: throw IllegalArgumentException("Service with ID ${service.id} not found")

        val updatedEntity = serviceMapper.updateEntity(existingEntity, service)
        val savedService = sessionFactory.withSession { session ->
            session.withTransaction {
                session.merge(updatedEntity)
            }
        }.awaitSuspending()
        return serviceMapper.toDomain(savedService)
    }

    override suspend fun getAllServices(): List<Service> {
        val services = sessionFactory.withSession {
            findAll().list()
        }.awaitSuspending()
        val domainServices = services.map { service -> serviceMapper.toDomain(service) }
        return domainServices
    }

    override suspend fun deleteService(id: UUID): Boolean {
        sessionFactory.withSession { session ->
            session.withTransaction {
                deleteById(id)
            }
        }.awaitSuspending()
        return true
    }

    override suspend fun findServicesByOrderedByDisplay(): List<Service> {
        val services = sessionFactory.withSession {
            find("ORDER BY displayOrder ASC").list()
        }.awaitSuspending()
        val domainServices = services.map { service -> serviceMapper.toDomain(service) }
        return domainServices
    }
}
