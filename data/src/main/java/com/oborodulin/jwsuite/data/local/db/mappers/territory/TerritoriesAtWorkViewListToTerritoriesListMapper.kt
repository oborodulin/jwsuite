package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesAtWorkView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoriesAtWorkViewListToTerritoriesListMapper(mapper: TerritoriesAtWorkViewToTerritoryMapper) :
    ListMapperImpl<TerritoriesAtWorkView, Territory>(mapper)