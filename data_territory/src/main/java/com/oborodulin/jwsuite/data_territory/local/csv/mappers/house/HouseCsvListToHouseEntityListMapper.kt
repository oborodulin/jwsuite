package com.oborodulin.jwsuite.data_territory.local.csv.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.HouseEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.HouseCsv

class HouseCsvListToHouseEntityListMapper(mapper: HouseCsvToHouseEntityMapper) :
    ListMapperImpl<HouseCsv, HouseEntity>(mapper)