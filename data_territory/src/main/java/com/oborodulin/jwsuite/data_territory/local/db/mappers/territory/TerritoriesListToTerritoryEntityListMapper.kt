package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.model.territory.Territory

class TerritoriesListToTerritoryEntityListMapper(mapper: TerritoryToTerritoryEntityMapper) :
    ListMapperImpl<Territory, TerritoryEntity>(mapper)