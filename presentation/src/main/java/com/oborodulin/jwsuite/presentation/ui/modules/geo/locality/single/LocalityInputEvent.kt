package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class LocalityInputEvent(val value: String) : Inputable {
    data class RegionId(val input: String) : LocalityInputEvent(input)
    data class RegionDistrictId(val input: String) : LocalityInputEvent(input)
    data class LocalityCode(val input: String) : LocalityInputEvent(input)
    data class LocalityType(val input: String) : LocalityInputEvent(input)
    data class LocalityName(val input: String) : LocalityInputEvent(input)
    data class LocalityShortName(val input: String) : LocalityInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
