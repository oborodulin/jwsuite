package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class PartialHousesInputEvent(val value: String) : Inputable {
    data class Street(val input: ListItemModel) : PartialHousesInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
