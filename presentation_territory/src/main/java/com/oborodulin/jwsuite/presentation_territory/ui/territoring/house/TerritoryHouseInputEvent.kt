package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoryHouseInputEvent(val value: String) : Inputable {
    data class Territory(val input: ListItemModel) : TerritoryHouseInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
