package com.oborodulin.jwsuite.data.local.db.mappers

import com.oborodulin.home.common.mapping.NullableInputListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.model.Event

class EventEntityListToEventsListMapper(mapper: EventEntityToEventMapper) :
    NullableInputListMapperImpl<EventEntity, Event>(mapper)