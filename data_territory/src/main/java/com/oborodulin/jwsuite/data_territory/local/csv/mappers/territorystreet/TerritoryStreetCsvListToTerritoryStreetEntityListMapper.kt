package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territorystreet

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryStreetEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryStreetCsv

class TerritoryStreetCsvListToTerritoryStreetEntityListMapper(mapper: TerritoryStreetCsvToTerritoryStreetEntityMapper) :
    ListMapperImpl<TerritoryStreetCsv, TerritoryStreetEntity>(mapper)