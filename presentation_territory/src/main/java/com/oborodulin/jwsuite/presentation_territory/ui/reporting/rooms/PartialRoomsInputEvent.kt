package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class PartialRoomsInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : PartialRoomsInputEvent(input.headline)
    data class Street(val input: ListItemModel) : PartialRoomsInputEvent(input.headline)

    override fun value(): String {
        return this.value
    }
}
