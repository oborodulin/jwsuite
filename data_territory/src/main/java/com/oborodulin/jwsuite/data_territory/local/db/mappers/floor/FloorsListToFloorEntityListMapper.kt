package com.oborodulin.jwsuite.data_territory.local.db.mappers.floor

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.territory.Floor

class FloorsListToFloorEntityListMapper(mapper: FloorToFloorEntityMapper) :
    ListMapperImpl<Floor, FloorEntity>(mapper)