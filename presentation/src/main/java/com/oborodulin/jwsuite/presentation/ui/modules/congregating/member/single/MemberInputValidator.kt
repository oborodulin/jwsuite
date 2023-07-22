package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class MemberInputValidator : Validatable {
    object MemberNum : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }
}
