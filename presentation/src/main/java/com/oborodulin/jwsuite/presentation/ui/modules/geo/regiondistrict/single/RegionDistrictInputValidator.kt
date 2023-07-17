package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Geo.RegionDistrictInputValidator"

sealed class RegionDistrictInputValidator : Validatable {
    object Region : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_empty_error
                else -> null
            }
    }

    object DistrictShortName : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_short_name_empty_error
                else -> null
            }
    }

    object DistrictName : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_name_empty_error
                else -> null
            }
    }
}
