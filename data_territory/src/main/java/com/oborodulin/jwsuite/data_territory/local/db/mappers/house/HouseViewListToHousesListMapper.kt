package com.oborodulin.jwsuite.data_territory.local.db.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.local.db.views.HouseView
import com.oborodulin.jwsuite.domain.model.territory.House

class HouseViewListToHousesListMapper(mapper: HouseViewToHouseMapper) :
    ListMapperImpl<HouseView, House>(mapper)