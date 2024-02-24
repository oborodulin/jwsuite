package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class CountryInputEvent(val value: String) : Inputable {
    data class CountryCode(val input: String) : CountryInputEvent(input)
    data class CountryName(val input: String) : CountryInputEvent(input)

    override fun value() = this.value
}
