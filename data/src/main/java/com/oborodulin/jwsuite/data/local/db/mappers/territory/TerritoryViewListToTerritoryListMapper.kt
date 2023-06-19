package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoryView
import com.oborodulin.jwsuite.domain.model.Territory

class TerritoryViewListToTerritoryListMapper(mapper: TerritoryViewToTerritoryMapper) :
    ListMapperImpl<TerritoryView, Territory>(mapper)