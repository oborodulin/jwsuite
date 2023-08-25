package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.details.street.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Territoring.LocalityInputValidator"

sealed class TerritoryStreetInputValidator : Validatable {
    object Region : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.region_empty_error
                else -> null
            }
    }

    object TerritoryStreetCode : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_code_empty_error
                //etc..
                else -> null
            }
    }

    object TerritoryStreetShortName : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_short_name_empty_error
                else -> null
            }
    }

    object TerritoryStreetName : TerritoryStreetInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_name_empty_error
                else -> null
            }
    }
}
