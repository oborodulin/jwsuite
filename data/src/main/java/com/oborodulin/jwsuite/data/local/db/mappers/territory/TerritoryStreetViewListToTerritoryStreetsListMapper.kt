package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.model.TerritoryStreet

class TerritoryStreetViewListToTerritoryStreetsListMapper(mapper: TerritoryStreetViewToTerritoryStreetMapper) :
    ListMapperImpl<TerritoryStreetView, TerritoryStreet>(mapper)