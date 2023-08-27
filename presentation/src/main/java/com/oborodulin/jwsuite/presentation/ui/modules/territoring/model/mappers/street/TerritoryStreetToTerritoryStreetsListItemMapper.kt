package com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.mappers.street

import com.oborodulin.home.common.mapping.Mapper
import com.oborodulin.jwsuite.domain.model.TerritoryStreet
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetsListItem
import java.util.UUID

class TerritoryStreetToTerritoryStreetsListItemMapper :
    Mapper<TerritoryStreet, TerritoryStreetsListItem> {
    override fun map(input: TerritoryStreet) = TerritoryStreetsListItem(
        id = input.id ?: UUID.randomUUID(),
        streetId = input.street.id!!,
        streetFullName = input.streetFullName,
        info = input.info
    )
}