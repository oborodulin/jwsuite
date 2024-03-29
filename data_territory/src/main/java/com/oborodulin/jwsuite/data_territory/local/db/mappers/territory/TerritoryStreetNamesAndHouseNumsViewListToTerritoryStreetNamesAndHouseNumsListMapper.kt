package com.oborodulin.jwsuite.data_territory.local.db.mappers.territory

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.TerritoryStreetNamesAndHouseNumsView
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreetNamesAndHouseNums

class TerritoryStreetNamesAndHouseNumsViewListToTerritoryStreetNamesAndHouseNumsListMapper(mapper: TerritoryStreetNamesAndHouseNumsViewToTerritoryStreetNamesAndHouseNumsMapper) :
    ListMapperImpl<TerritoryStreetNamesAndHouseNumsView, TerritoryStreetNamesAndHouseNums>(mapper)