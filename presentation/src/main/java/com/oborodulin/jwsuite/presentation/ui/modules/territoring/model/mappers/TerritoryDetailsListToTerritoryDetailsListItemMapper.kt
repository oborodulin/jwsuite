package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.ListMapperImpl
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem

class TerritoryDetailsListToTerritoryDetailsListItemMapper(mapper: TerritoryDetailToTerritoryDetailsListItemMapper) :
    ListMapperImpl<TerritoryDetail, TerritoryDetailsListItem>(mapper)