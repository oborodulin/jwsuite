package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Territoring.TerritoryStreetInputValidator"

sealed class TerritoryStreetInputValidator : Validatable {
    data object Street : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> com.oborodulin.jwsuite.presentation_geo.R.string.street_empty_error
                else -> null
            }
    }

    data object EstHouses : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? {
            val isNeedAddEstHouses = inputs[1]?.toBooleanStrictOrNull()
            return when {
                isNeedAddEstHouses == true && inputs[0].isNullOrBlank() -> R.string.territory_street_est_houses_empty_error
                else -> null
            }
        }
    }
}