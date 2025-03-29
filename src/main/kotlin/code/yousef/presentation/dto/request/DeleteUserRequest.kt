package code.yousef.presentation.dto.request

import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class DeleteUserRequest @OptIn(ExperimentalUuidApi::class) constructor(
    val id: UUID
)