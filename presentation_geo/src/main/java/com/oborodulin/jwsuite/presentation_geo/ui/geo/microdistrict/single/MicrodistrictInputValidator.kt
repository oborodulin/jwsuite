package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.MicrodistrictInputValidator"

sealed class MicrodistrictInputValidator : Validatable {
    data object Locality : MicrodistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_empty_error
                else -> null
            }
    }

    data object LocalityDistrict : MicrodistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_district_empty_error
                //etc..
                else -> null
            }
    }

    data object MicrodistrictShortName : MicrodistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.microdistrict_short_name_empty_error
                else -> null
            }
    }

    data object MicrodistrictName : MicrodistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.microdistrict_name_empty_error
                else -> null
            }
    }
}
