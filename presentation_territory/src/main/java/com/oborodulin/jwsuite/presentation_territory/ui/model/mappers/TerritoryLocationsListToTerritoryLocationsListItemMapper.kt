package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.TerritoryLocation
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem

class TerritoryLocationsListToTerritoryLocationsListItemMapper(mapper: TerritoryLocationToTerritoryLocationsListItemMapper) :
    ListMapperImpl<TerritoryLocation, TerritoryLocationsListItem>(mapper)