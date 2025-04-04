package code.yousef.infrastructure.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Converter(autoApply = true)
class InstantAttributeConverter : AttributeConverter<Instant, LocalDateTime> {

    override fun convertToDatabaseColumn(attribute: Instant?): LocalDateTime? {
        return attribute?.let {
            val localDateTime = LocalDateTime.ofInstant(it, ZoneOffset.UTC)
            LocalDateTime.of(
                localDateTime.year,
                localDateTime.month,
                localDateTime.dayOfMonth,
                localDateTime.hour,
                localDateTime.minute,
                localDateTime.second,
                localDateTime.nano
            )
        }
    }

    override fun convertToEntityAttribute(dbData: LocalDateTime?): Instant? {
        return dbData?.let {
            val utcInstant = it.toInstant(ZoneOffset.UTC)
            Instant.ofEpochSecond(
                utcInstant.epochSecond,
                utcInstant.nano.toLong()
            )
        }
    }
} 