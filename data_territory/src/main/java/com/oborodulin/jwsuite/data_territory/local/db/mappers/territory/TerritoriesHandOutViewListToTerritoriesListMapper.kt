package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoriesHandOutView
import com.oborodulin.jwsuite.domain.model.territory.Territory

class TerritoriesHandOutViewListToTerritoriesListMapper(mapper: TerritoriesHandOutViewToTerritoryMapper) :
    ListMapperImpl<TerritoriesHandOutView, Territory>(mapper)