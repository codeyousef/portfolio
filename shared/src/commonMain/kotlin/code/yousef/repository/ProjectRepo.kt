package code.yousef.repository

import code.yousef.model.Project
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ProjectRepo {
    suspend fun findProjectById(id: Uuid): Project?
    suspend fun findFeaturedProjects(): List<Project>
    suspend fun saveProject(project: Project): Project
    suspend fun getAllProjects(): List<Project>
    suspend fun deleteProject(id: Uuid): Boolean
    suspend fun findByTechnology(technology: String): List<Project>
} 