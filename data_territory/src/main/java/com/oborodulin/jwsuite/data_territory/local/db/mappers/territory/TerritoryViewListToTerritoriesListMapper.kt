package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoryViewListToTerritoriesListMapper(mapper: TerritoryViewToTerritoryMapper) :
    ListMapperImpl<TerritoryView, Territory>(mapper)