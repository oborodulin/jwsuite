package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Territoring.TerritoryHouseInputValidator"

sealed class StreetLocalityDistrictInputValidator : Validatable {
    data object House : StreetLocalityDistrictInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.house_empty_error
                else -> null
            }
    }
}
