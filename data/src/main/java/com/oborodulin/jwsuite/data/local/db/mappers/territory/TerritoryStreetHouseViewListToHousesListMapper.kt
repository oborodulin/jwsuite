package com.oborodulin.jwsuite.data.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data.local.db.views.TerritoryStreetHouseView
import com.oborodulin.jwsuite.domain.model.House

class TerritoryStreetHouseViewListToHousesListMapper(mapper: TerritoryStreetHouseViewToHouseMapper) :
    ListMapperImpl<TerritoryStreetHouseView, House>(mapper)