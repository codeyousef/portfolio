package code.yousef.repository

import code.yousef.model.Project
import java.util.UUID

interface ProjectRepo {
    suspend fun findProjectById(id: UUID): Project?
    suspend fun findFeaturedProjects(): List<Project>
    suspend fun saveProject(project: Project): Project
    suspend fun getAllProjects(): List<Project>
    suspend fun deleteProject(id: UUID): Boolean
    suspend fun findByTechnology(technology: String): List<Project>
} 