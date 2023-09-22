package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class HousesInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : HousesInputEvent(input.headline)
    data class Street(val input: ListItemModel) : HousesInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
