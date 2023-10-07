package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.floor

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.Floor
import com.oborodulin.jwsuite.presentation_territory.ui.model.FloorsListItem

class FloorsListToFloorsListItemMapper(mapper: FloorToFloorsListItemMapper) :
    ListMapperImpl<Floor, FloorsListItem>(mapper)