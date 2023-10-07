package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.domain.model.territory.Territory

class TerritoriesAtWorkViewListToTerritoriesListMapper(mapper: TerritoriesAtWorkViewToTerritoryMapper) :
    ListMapperImpl<TerritoriesAtWorkView, Territory>(mapper)