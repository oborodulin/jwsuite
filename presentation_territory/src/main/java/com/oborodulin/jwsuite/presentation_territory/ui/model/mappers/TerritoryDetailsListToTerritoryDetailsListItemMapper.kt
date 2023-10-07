package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.territory.TerritoryDetail
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryDetailsListItem

class TerritoryDetailsListToTerritoryDetailsListItemMapper(mapper: TerritoryDetailToTerritoryDetailsListItemMapper) :
    ListMapperImpl<TerritoryDetail, TerritoryDetailsListItem>(mapper)