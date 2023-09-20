package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.House
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

class EntrancesListToEntrancesListItemMapper(mapper: EntranceToEntrancesListItemMapper) :
    ListMapperImpl<House, HousesListItem>(mapper)