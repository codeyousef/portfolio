package code.yousef.presentation.dto.response

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class BlogResponse(
    val id: Uuid,
    val title: String,
    val summary: String,
    val content: String,
    val imageUrl: String,
    val tags: List<String>,
    val publishDate: String,
    val createdAt: String,
    val updatedAt: String,
    val published: Boolean,
    val slug: String,
    val readingTime: Int
)