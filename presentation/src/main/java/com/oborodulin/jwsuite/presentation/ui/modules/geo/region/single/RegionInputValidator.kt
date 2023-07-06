package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Geo.ui.RegionInputValidator"

sealed class RegionInputValidator : Validatable {
    object RegionCode : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_code_empty_error
                //etc..
                else -> null
            }
    }

    object RegionName : RegionInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_name_empty_error
                else -> null
            }
    }
}