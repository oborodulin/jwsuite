package com.oborodulin.jwsuite.data.local.db.mappers

data class EventMappers(
    val eventEntityListToEventsListMapper: EventEntityListToEventsListMapper,
    val eventEntityToEventMapper: EventEntityToEventMapper,
    val eventToEventEntityMapper: EventToEventEntityMapper
)
