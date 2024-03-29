package com.oborodulin.jwsuite.presentation_territory.ui.model

import com.oborodulin.home.common.extensions.toStreetName
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.model.ModelUi
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import java.util.UUID

data class TerritoryStreetUi(
    val territoryId: UUID = UUID.randomUUID(),
    val street: StreetUi = StreetUi(),
    val isEvenSide: Boolean? = null,
    val isPrivateSector: Boolean? = null,
    val estimatedHouses: Int? = null,
    val isNeedAddEstHouses: Boolean = false,
    val isExistsHouses: Boolean = false
) : ModelUi()

fun ListItemModel?.toTerritoryStreetUi() =
    TerritoryStreetUi(street = StreetUi(streetName = this?.headline.toStreetName())).also {
        it.id = this?.itemId
    }