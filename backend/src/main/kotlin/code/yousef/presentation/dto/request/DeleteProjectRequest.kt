package code.yousef.presentation.dto.request

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class DeleteProjectRequest(
    val id: Uuid
)