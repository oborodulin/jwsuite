package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Territoring.TerritoryStreetInputValidator"

sealed class TerritoryStreetInputValidator : Validatable {
    data object Street : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.street_empty_error
                else -> null
            }
    }
}
