package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Congregating.CongregationInputValidator"

sealed class CongregationInputValidator : Validatable {
    object Locality : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.locality_empty_error
                else -> null
            }
    }

    object CongregationNum : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    object CongregationName : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.congregation_name_empty_error
                else -> null
            }
    }

    object TerritoryMark : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_mark_empty_error
                else -> null
            }
    }

}
