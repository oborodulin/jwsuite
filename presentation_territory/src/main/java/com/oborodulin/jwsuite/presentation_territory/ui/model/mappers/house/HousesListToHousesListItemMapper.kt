package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.house

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.House
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

class HousesListToHousesListItemMapper(mapper: HouseToHousesListItemMapper) :
    ListMapperImpl<House, HousesListItem>(mapper)