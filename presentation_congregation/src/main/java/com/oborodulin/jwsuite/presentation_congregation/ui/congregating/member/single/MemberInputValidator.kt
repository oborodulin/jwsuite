package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class MemberInputValidator : Validatable {
    data object Group : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_group_empty_error
                else -> null
            }
    }

    data object MemberNum : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
                //etc..
                else -> null
            }
    }

    data object Pseudonym : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_pseudonym_empty_error
                //etc..
                else -> null
            }
    }

    data object PhoneNumber : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*    when {
                !inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
                //etc..
                else -> null
            }
         */
    }

    data object MemberType : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBirth : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBaptism : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object InactiveDate : MemberInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }
}
