package com.oborodulin.jwsuite.presentation_territory.ui.reporting.single

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_territory.R

private const val TAG = "Reporting.MemberReportInputValidator"

sealed class MemberReportInputValidator : Validatable {
    data object TerritoryStreet : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_street_empty_error
                else -> null
            }
    }

    data object House : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_house_empty_error
                else -> null
            }
    }

    data object Room : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.territory_room_empty_error
                else -> null
            }
    }

    data object MemberReportMark : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.member_report_mark_empty_error
                //etc..
                else -> null
            }
    }

    data object Language : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.language_empty_error
                //etc..
                else -> null
            }
    }

    data object Age : MemberReportInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? = null
        /*when {
            inputs[0].isNullOrBlank() -> R.string.member_num_empty_error
            //etc..
            else -> null
        }*/
    }
}