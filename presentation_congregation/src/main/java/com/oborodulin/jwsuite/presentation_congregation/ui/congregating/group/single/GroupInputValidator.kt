package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class GroupInputValidator : Validatable {
    data object GroupNum : GroupInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }
}