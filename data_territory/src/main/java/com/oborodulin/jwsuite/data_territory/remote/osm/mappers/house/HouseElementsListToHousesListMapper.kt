package com.oborodulin.jwsuite.data_territory.remote.osm.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.data_territory.remote.osm.model.house.HouseElement
import com.oborodulin.jwsuite.domain.model.territory.House

class HouseElementsListToHousesListMapper(mapper: HouseElementToHouseMapper) :
    ListMapperImpl<HouseElement, House>(mapper)