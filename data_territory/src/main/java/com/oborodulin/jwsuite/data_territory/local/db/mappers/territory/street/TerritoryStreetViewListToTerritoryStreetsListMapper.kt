package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory.street

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet

class TerritoryStreetViewListToTerritoryStreetsListMapper(mapper: TerritoryStreetViewToTerritoryStreetMapper) :
    ListMapperImpl<TerritoryStreetView, TerritoryStreet>(mapper)