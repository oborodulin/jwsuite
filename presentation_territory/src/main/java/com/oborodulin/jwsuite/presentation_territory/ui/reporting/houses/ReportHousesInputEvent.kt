package com.oborodulin.jwsuite.presentation_territory.ui.reporting.houses

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class ReportHousesInputEvent(val value: String) : Inputable {
    data class Street(val input: ListItemModel) : ReportHousesInputEvent(input.headline)

    override fun value() = this.value
}
