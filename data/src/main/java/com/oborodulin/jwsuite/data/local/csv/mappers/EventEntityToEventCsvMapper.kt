package com.oborodulin.jwsuite.data.local.csv.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.services.csv.model.EventCsv

class EventEntityToEventCsvMapper : Mapper<EventEntity, EventCsv> {
    override fun map(input: EventEntity) = EventCsv(
        eventId = input.eventId,
        eventType = input.eventType,
        eventTime = input.eventTime,
        isManual = input.isManual,
        isSuccess = input.isSuccess
    )
}