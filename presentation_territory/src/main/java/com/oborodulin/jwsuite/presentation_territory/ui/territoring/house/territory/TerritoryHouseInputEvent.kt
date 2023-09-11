package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryHouseInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : TerritoryHouseInputEvent(input.headline)
    data class House(val input: ListItemModel) : TerritoryHouseInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}