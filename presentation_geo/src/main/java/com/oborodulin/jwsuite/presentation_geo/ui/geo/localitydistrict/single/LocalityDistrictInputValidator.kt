package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Geo.RegionDistrictInputValidator"

sealed class LocalityDistrictInputValidator : Validatable {
    data object Locality : LocalityDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_empty_error
                else -> null
            }
    }

    data object DistrictShortName : LocalityDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_district_short_name_empty_error
                else -> null
            }
    }

    data object DistrictName : LocalityDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.locality_district_name_empty_error
                else -> null
            }
    }
}
