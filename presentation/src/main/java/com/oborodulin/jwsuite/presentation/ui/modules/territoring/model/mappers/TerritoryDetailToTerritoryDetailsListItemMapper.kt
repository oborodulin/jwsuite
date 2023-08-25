package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryDetail
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryDetailsListItem

class TerritoryDetailToTerritoryDetailsListItemMapper :
    Mapper<TerritoryDetail, TerritoryDetailsListItem> {
    override fun map(input: TerritoryDetail) = TerritoryDetailsListItem(
        territoryStreetId = input.territoryStreetId,
        streetId = input.street.id!!,
        streetInfo = input.streetInfo,
        housesInfo = input.housesInfo,
        entrancesInfo = input.entrancesInfo,
        roomsInfo = input.roomsInfo
    )
}