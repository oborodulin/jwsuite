package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem

sealed class StreetMicrodistrictInputEvent(val value: String) : Inputable {
    data class Street(val input: StreetsListItem) : StreetMicrodistrictInputEvent(input.headline)

    override fun value() = this.value
}
