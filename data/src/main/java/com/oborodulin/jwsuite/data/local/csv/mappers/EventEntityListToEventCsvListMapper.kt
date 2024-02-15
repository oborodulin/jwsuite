package com.oborodulin.jwsuite.data.local.csv.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.EventEntity
import com.oborodulin.jwsuite.domain.services.csv.model.EventCsv

class EventEntityListToEventCsvListMapper(mapper: EventEntityToEventCsvMapper) :
    ListMapperImpl<EventEntity, EventCsv>(mapper)