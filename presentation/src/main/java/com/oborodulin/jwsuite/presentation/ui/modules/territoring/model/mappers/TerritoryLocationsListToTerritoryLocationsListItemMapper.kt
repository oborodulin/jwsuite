package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.TerritoryLocation
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryLocationsListItem

class TerritoryLocationsListToTerritoryLocationsListItemMapper(mapper: TerritoryLocationToTerritoryLocationsListItemMapper) :
    ListMapperImpl<TerritoryLocation, TerritoryLocationsListItem>(mapper)