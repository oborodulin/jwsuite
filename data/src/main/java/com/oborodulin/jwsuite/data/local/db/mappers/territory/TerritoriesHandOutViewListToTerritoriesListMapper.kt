package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoriesHandOutViewListToTerritoriesListMapper(mapper: TerritoriesHandOutViewToTerritoryMapper) :
    ListMapperImpl<TerritoriesHandOutView, Territory>(mapper)