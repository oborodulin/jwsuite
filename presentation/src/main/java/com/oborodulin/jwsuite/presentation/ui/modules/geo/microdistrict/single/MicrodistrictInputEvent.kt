package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class MicrodistrictInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : MicrodistrictInputEvent(input.headline)
    data class LocalityDistrict(val input: ListItemModel) : MicrodistrictInputEvent(input.headline)
    data class MicrodistrictShortName(val input: String) : MicrodistrictInputEvent(input)
    data class MicrodistrictType(val input: String) : MicrodistrictInputEvent(input)
    data class MicrodistrictName(val input: String) : MicrodistrictInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
