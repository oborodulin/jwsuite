package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem

class TerritoryStreetsListToTerritoryStreetsListItemMapper(mapper: TerritoryStreetToTerritoryStreetsListItemMapper) :
    ListMapperImpl<TerritoryStreet, TerritoryStreetsListItem>(mapper)