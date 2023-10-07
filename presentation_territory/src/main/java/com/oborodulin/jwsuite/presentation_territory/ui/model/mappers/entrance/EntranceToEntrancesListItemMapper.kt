package com.oborodulin.jwsuite.presentation_territory.ui.model.mappers.entrance

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.territory.Entrance
import com.oborodulin.jwsuite.presentation_territory.ui.model.EntrancesListItem
import java.util.UUID

class EntranceToEntrancesListItemMapper : Mapper<Entrance, EntrancesListItem> {
    override fun map(input: Entrance) = EntrancesListItem(
        id = input.id ?: UUID.randomUUID(),
        entranceNum = input.entranceNum,
        isSecurity = input.isSecurity,
        isIntercom = input.isIntercom,
        isResidential = input.isResidential,
        entranceDesc = input.entranceDesc,
        houseFullNum = input.house.houseFullNum,
        entranceFullNum = input.entranceFullNum,
        territoryFullCardNum = input.territoryFullCardNum,
        info = input.info
    )
}