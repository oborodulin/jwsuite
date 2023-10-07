package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.street

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.TerritoryStreet
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetsListItem

class TerritoryStreetsListToTerritoryStreetsListItemMapper(mapper: TerritoryStreetToTerritoryStreetsListItemMapper) :
    ListMapperImpl<TerritoryStreet, TerritoryStreetsListItem>(mapper)