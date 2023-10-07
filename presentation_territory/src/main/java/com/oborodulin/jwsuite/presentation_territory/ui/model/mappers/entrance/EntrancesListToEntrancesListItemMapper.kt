package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntrancesListItem

class EntrancesListToEntrancesListItemMapper(mapper: EntranceToEntrancesListItemMapper) :
    ListMapperImpl<Entrance, EntrancesListItem>(mapper)