package com.oborodulin.jwsuite.data_territory.local.csv.mappers.room

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.RoomEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.RoomCsv

class RoomCsvListToRoomEntityListMapper(mapper: RoomCsvToRoomEntityMapper) :
    ListMapperImpl<RoomCsv, RoomEntity>(mapper)