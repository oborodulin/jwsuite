package com.oborodulin.jwsuite.data.local.db.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.model.Event
import java.util.UUID

class EventToEventEntityMapper : Mapper<Event, EventEntity> {
    override fun map(input: Event) = EventEntity(
        eventId = input.id ?: input.apply { id = UUID.randomUUID() }.id!!,
        eventType = input.eventType,
        eventTime = input.eventTime,
        isManual = input.isManual,
        isSuccess = input.isSuccess
    )
}