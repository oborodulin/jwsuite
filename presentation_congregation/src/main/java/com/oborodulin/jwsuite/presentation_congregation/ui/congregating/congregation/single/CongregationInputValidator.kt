package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Congregating.CongregationInputValidator"

sealed class CongregationInputValidator : Validatable {
    data object Locality : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> com.oborodulin.jwsuite.presentation_geo.R.string.locality_empty_error
                else -> null
            }
    }

    data object CongregationNum : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    data object CongregationName : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.congregation_name_empty_error
                else -> null
            }
    }

    data object TerritoryMark : CongregationInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_mark_empty_error
                else -> null
            }
    }
}
