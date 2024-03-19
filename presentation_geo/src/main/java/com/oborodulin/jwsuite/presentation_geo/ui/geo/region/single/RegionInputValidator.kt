package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.RegionInputValidator"

sealed class RegionInputValidator : Validatable {
    data object Country : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.country_empty_error
                //etc..
                else -> null
            }
    }

    data object RegionCode : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.region_code_empty_error
                //etc..
                else -> null
            }
    }

    data object RegionName : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.region_name_empty_error
                else -> null
            }
    }
}
