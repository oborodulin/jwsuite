package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class LocalityInputEvent(val value: String) : Inputable {
    data class Region(val input: ListItemModel) : LocalityInputEvent(input.headline)
    data class RegionDistrict(val input: ListItemModel) : LocalityInputEvent(input.headline)
    data class LocalityCode(val input: String) : LocalityInputEvent(input)
    data class LocalityType(val input: String) : LocalityInputEvent(input)
    data class LocalityName(val input: String) : LocalityInputEvent(input)
    data class LocalityShortName(val input: String) : LocalityInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
