package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.StreetInputValidator"

sealed class StreetInputValidator : Validatable {
    data object Locality : StreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_empty_error
                else -> null
            }
    }

    data object StreetName : StreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.street_name_empty_error
                else -> null
            }
    }
}
