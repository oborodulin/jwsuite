package com.oborodulin.jwsuite.data.local.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oborodulin.home.common.data.OffsetDateTimeSerializer
import com.oborodulin.home.common.data.UUIDSerializer
import com.oborodulin.home.common.data.entities.BaseEntity
import com.oborodulin.jwsuite.domain.types.EventType
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(
    tableName = EventEntity.TABLE_NAME,
    indices = [Index(value = ["eventType", "eventTime", "isManual"], unique = true)]
)
@Serializable
data class EventEntity(
    @Serializable(with = UUIDSerializer::class)
    @PrimaryKey val eventId: UUID = UUID.randomUUID(),
    val eventType: EventType,
    @Serializable(with = OffsetDateTimeSerializer::class)
    val eventTime: OffsetDateTime = OffsetDateTime.now(),
    val isManual: Boolean,
    val isSuccess: Boolean
) : BaseEntity() {
    companion object {
        const val TABLE_NAME = "events"

        fun defaultEvent(
            eventId: UUID = UUID.randomUUID(),
            eventType: EventType,
            eventTime: OffsetDateTime = OffsetDateTime.now(),
            isManual: Boolean, isSuccess: Boolean
        ) = EventEntity(
            eventId = eventId, eventType = eventType, eventTime = eventTime, isManual = isManual,
            isSuccess = isSuccess
        )
    }

    override fun id() = this.eventId

    override fun key(): Int {
        var result = eventType.hashCode()
        result = result * 31 + eventTime.hashCode()
        result = result * 31 + isManual.hashCode()
        return result
    }

    override fun toString(): String {
        val str = StringBuffer()
        str.append("Event Entity: ")
            .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(eventTime)).append(" '")
            .append(eventType).append("' ")
            .append(" [isWorker = ").append(isManual)
            .append("] eventId = ").append(eventId)
        return str.toString()
    }
}