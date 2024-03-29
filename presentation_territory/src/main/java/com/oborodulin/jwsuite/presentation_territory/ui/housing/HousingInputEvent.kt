package com.oborodulin.jwsuite.presentation_territory.ui.housing

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class HousingInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : HousingInputEvent(input.headline)
    data class Street(val input: ListItemModel) : HousingInputEvent(input.headline)

    override fun value() = this.value
}
