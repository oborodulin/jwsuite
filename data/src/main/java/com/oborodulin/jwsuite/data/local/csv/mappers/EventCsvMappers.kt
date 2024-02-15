package com.oborodulin.jwsuite.data.local.csv.mappers

data class EventCsvMappers(
    val eventEntityListToEventCsvListMapper: EventEntityListToEventCsvListMapper,
    val eventCsvListToEventEntityListMapper: EventCsvListToEventEntityListMapper
)
