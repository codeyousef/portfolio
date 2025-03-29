package code.yousef.domain.repository

import code.yousef.domain.model.Project
import java.util.*

interface ProjectRepo {
    suspend fun findProjectById(id: UUID): Project?
    suspend fun findFeaturedProjects(): List<Project>
    suspend fun saveProject(project: Project): Project
    suspend fun getAllProjects(): List<Project>
    suspend fun deleteProject(id: UUID): Boolean
    suspend fun findByTechnology(technology: String): List<Project>
}
