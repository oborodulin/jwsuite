package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryStreetInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : TerritoryStreetInputEvent(input.headline)
    data class Street(val input: ListItemModel) : TerritoryStreetInputEvent(input.headline)
    data class IsPrivateSector(val input: Boolean?) : TerritoryStreetInputEvent(input.toString())
    data class IsEvenSide(val input: Boolean?) : TerritoryStreetInputEvent(input.toString())
    data class EstHouses(val input: String) : TerritoryStreetInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
