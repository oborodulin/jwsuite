package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem

sealed class ReportRoomsInputEvent(val value: String) : Inputable {
    data class House(val input: HousesListItem) : ReportRoomsInputEvent(input.headline)

    override fun value() = this.value
}
