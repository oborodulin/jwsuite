package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class HouseInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class Street(val input: ListItemModel) : HouseInputEvent(input.headline)
    data class IsPrivateSector(val input: Boolean?) : HouseInputEvent(input.toString())
    data class IsEvenSide(val input: Boolean?) : HouseInputEvent(input.toString())
    data class EstHouses(val input: String) : HouseInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
