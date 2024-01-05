package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_geo.R

private const val TAG = "Housing.TerritoryStreetInputValidator"

sealed class HouseInputValidator : Validatable {
    data object Locality : HouseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_empty_error
                else -> null
            }
    }
    data object Street : HouseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.street_empty_error
                else -> null
            }
    }

    data object HouseNum : HouseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation_territory.R.string.house_num_empty_error
                else -> null
            }
    }

    data object BuildingType : HouseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation_territory.R.string.building_type_empty_error
                else -> null
            }
    }
}
