package com.oborodulin.jwsuite.data_territory.local.csv.mappers.floor

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.FloorEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.FloorCsv

class FloorEntityListToFloorCsvListMapper(mapper: FloorEntityToFloorCsvMapper) :
    ListMapperImpl<FloorEntity, FloorCsv>(mapper)