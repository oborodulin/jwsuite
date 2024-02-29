package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single.RegionInputValidator

private const val TAG = "Geo.RegionDistrictInputValidator"

sealed class RegionDistrictInputValidator : Validatable {
    data object Country : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.country_empty_error
                //etc..
                else -> null
            }
    }
    data object Region : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_empty_error
                else -> null
            }
    }

    data object DistrictShortName : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_district_short_name_empty_error
                else -> null
            }
    }

    data object DistrictName : RegionDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_district_name_empty_error
                else -> null
            }
    }
}
