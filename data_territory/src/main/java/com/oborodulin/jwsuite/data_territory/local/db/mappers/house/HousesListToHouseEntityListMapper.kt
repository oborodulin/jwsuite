package com.oborodulin.jwsuite.data_territory.local.db.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.model.territory.House

class HousesListToHouseEntityListMapper(mapper: HouseToHouseEntityMapper) :
    ListMapperImpl<House, HouseEntity>(mapper)