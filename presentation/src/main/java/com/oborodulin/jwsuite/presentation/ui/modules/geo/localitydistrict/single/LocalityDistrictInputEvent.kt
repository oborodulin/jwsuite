package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class LocalityDistrictInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : LocalityDistrictInputEvent(input.headline)
    data class DistrictShortName(val input: String) : LocalityDistrictInputEvent(input)
    data class DistrictName(val input: String) : LocalityDistrictInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
