package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.Territory
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoriesListItem

class TerritoriesListToTerritoriesListItemMapper(mapper: TerritoryToTerritoriesListItemMapper) :
    ListMapperImpl<Territory, TerritoriesListItem>(mapper)