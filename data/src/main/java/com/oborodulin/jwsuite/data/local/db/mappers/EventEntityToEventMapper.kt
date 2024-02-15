package com.oborodulin.jwsuite.data.local.db.mappers

import android.content.Context
import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.model.Event

class EventEntityToEventMapper(private val ctx: Context) : Mapper<EventEntity, Event> {
    override fun map(input: EventEntity): Event {
        val event = Event(
            ctx = ctx,
            eventType = input.eventType,
            eventTime = input.eventTime,
            isManual = input.isManual,
            isSuccess = input.isSuccess
        )
        event.id = input.eventId
        return event
    }
}