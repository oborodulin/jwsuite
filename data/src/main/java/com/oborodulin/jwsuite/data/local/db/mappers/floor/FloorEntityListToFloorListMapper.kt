package com.oborodulin.jwsuite.data.local.db.mappers.floor

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.model.Floor

class FloorEntityListToFloorListMapper(mapper: FloorEntityToFloorMapper) :
    ListMapperImpl<FloorEntity, Floor>(mapper)