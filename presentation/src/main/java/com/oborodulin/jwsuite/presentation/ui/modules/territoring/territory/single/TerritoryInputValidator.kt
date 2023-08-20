package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class TerritoryInputValidator : Validatable {
    data object Category : TerritoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_category_empty_error
                else -> null
            }
    }

    data object Locality : TerritoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_empty_error
                //etc..
                else -> null
            }
    }

    data object TerritoryNum : TerritoryInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_num_empty_error
                //etc..
                else -> null
            }
    }
}