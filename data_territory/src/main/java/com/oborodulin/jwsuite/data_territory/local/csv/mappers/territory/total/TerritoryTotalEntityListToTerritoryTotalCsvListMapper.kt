package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory.total

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryTotalEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryTotalCsv

class TerritoryTotalEntityListToTerritoryTotalCsvListMapper(mapper: TerritoryTotalEntityToTerritoryTotalCsvMapper) :
    ListMapperImpl<TerritoryTotalEntity, TerritoryTotalCsv>(mapper)