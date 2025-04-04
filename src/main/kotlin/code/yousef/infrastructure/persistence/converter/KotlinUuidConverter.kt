package code.yousef.infrastructure.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.util.UUID
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toKotlinUuid

/**
 * Converts between java.util.UUID and kotlin.uuid.Uuid for JPA
 */
@OptIn(ExperimentalUuidApi::class)
@Converter(autoApply = true)
class KotlinUuidConverter : AttributeConverter<Uuid?, UUID?> {
    override fun convertToDatabaseColumn(attribute: Uuid?): UUID? {
        return attribute?.let { UUID.fromString(it.toString()) }
    }

    override fun convertToEntityAttribute(dbData: UUID?): Uuid? {
        return dbData?.toKotlinUuid()
    }
} 