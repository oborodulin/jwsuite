package com.oborodulin.jwsuite.data.local.db.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.House

class HouseListToHouseEntityListMapper(mapper: HouseToHouseEntityMapper) :
    ListMapperImpl<House, HouseEntity>(mapper)