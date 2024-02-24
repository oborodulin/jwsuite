package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.CountryInputValidator"

sealed class CountryInputValidator : Validatable {
    data object CountryCode : CountryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.country_code_empty_error
                //etc..
                else -> null
            }
    }

    data object CountryName : CountryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.country_name_empty_error
                else -> null
            }
    }
}
