package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_congregation.R

private const val TAG = "Reporting.GroupInputValidator"

sealed class MemberReportInputValidator : Validatable {
    data object House : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.member_congregation_empty_error
                else -> null
            }
    }

    data object Room : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_group_empty_error
            else -> null
        }*/
    }

    data object ReportMark : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> com.oborodulin.jwsuite.presentation.R.string.num_empty_error
            //etc..
            else -> null
        }
     */
    }

    data object Gender : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
            //etc..
            else -> null
        }*/
    }

    data object Age : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrEmpty() -> R.string.member_num_empty_error
            //etc..
            else -> null
        }*/
    }
}