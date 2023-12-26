package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Congregating.GroupInputValidator"

sealed class MemberReportInputValidator : Validatable {
    data object Congregation : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
        when {
            inputs[0].isNullOrEmpty() -> R.string.member_congregation_empty_error
            else -> null
        }
    }
    data object Group : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_group_empty_error
            else -> null
        }*/
    }

    data object MemberReportNum : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
            //etc..
            else -> null
        }*/
    }

    data object Pseudonym : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_pseudonym_empty_error
                //etc..
                else -> null
            }
    }

    data object PhoneNumber : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*    when {
                !inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
                //etc..
                else -> null
            }
         */
    }

    data object MemberReportType : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBirth : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object DateOfBaptism : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object LoginExpiredDate : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object MovementDate : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_movement_date_empty_error
                //etc..
                else -> null
            }
    }
}
