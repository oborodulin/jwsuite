package com.oborodulin.jwsuite.data.local.csv.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.services.csv.model.EventCsv

class EventCsvToEventEntityMapper : Mapper<EventCsv, EventEntity> {
    override fun map(input: EventCsv) = EventEntity(
        eventId = input.eventId,
        eventType = input.eventType,
        eventTime = input.eventTime,
        isManual = input.isManual,
        isSuccess = input.isSuccess
    )
}