package code.yousef.infrastructure.converter

import jakarta.ws.rs.ext.ParamConverter
import jakarta.ws.rs.ext.ParamConverterProvider
import jakarta.ws.rs.ext.Provider
import java.lang.reflect.Type
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Provider
class UuidConverterProvider : ParamConverterProvider {
    override fun <T> getConverter(
        rawType: Class<T>,
        genericType: Type,
        annotations: Array<out Annotation>
    ): ParamConverter<T>? {
        if (rawType != Uuid::class.java) {
            return null
        }
        
        @Suppress("UNCHECKED_CAST")
        return UuidConverter() as ParamConverter<T>
    }
}

@OptIn(ExperimentalUuidApi::class)
class UuidConverter : ParamConverter<Uuid> {
    override fun fromString(value: String?): Uuid? {
        if (value == null || value.isBlank()) {
            return null
        }
        return try {
            Uuid.parse(value)
        } catch (e: Exception) {
            null
        }
    }

    override fun toString(value: Uuid?): String? {
        return value?.toString()
    }
} 