package code.yousef.infrastructure.persistence.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneOffset

@Converter(autoApply = true)
class InstantAttributeConverter : AttributeConverter<Instant, java.time.LocalDateTime> {
    
    override fun convertToDatabaseColumn(attribute: Instant?): java.time.LocalDateTime? {
        return attribute?.let {
            val localDateTime = it.toLocalDateTime(TimeZone.UTC)
            java.time.LocalDateTime.of(
                localDateTime.year,
                localDateTime.monthNumber,
                localDateTime.dayOfMonth,
                localDateTime.hour,
                localDateTime.minute,
                localDateTime.second,
                localDateTime.nanosecond
            )
        }
    }
    
    override fun convertToEntityAttribute(dbData: java.time.LocalDateTime?): Instant? {
        return dbData?.let {
            val utcInstant = it.toInstant(ZoneOffset.UTC)
            Instant.fromEpochSeconds(
                utcInstant.epochSecond,
                utcInstant.nano.toLong()
            )
        }
    }
} 