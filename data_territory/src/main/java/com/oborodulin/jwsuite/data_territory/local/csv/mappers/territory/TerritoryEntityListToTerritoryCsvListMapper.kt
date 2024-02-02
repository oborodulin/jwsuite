package com.oborodulin.jwsuite.data_territory.local.csv.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.services.csv.model.territory.TerritoryCsv

class TerritoryEntityListToTerritoryCsvListMapper(mapper: TerritoryEntityToTerritoryCsvMapper) :
    ListMapperImpl<TerritoryEntity, TerritoryCsv>(mapper)