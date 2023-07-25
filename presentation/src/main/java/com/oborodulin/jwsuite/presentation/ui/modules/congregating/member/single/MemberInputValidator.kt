package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class MemberInputValidator : Validatable {
    object Group : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_group_empty_error
                else -> null
            }
    }

    object MemberNum : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
                //etc..
                else -> null
            }
    }

    object Pseudonym : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_pseudonym_empty_error
                //etc..
                else -> null
            }
    }

    object PhoneNumber : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                !inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    object MemberType : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    object DateOfBirth : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    object DateOfBaptism : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }

    object InactiveDate : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.num_empty_error
                //etc..
                else -> null
            }
    }
}
