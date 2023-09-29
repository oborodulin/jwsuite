package com.oborodulin.jwsuite.presentation.ui.register

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.RegionInputValidator"

sealed class RegisterInputValidator : Validatable {
    data object Username : RegisterInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_code_empty_error
                //etc..
                else -> null
            }
    }

    data object Password : RegisterInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_name_empty_error
                else -> null
            }
    }
}
