package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.entities.TerritoryEntity
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoriesListToTerritoryEntityListMapper(mapper: TerritoryToTerritoryEntityMapper) :
    ListMapperImpl<Territory, TerritoryEntity>(mapper)