package com.oborodulin.jwsuite.presentation_dashboard.ui.database

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_dashboard.R

private const val TAG = "Presentation_Dashboard.DatabaseInputValidator"

sealed class DatabaseInputValidator : Validatable {
    data object ReceiverMember : DatabaseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrBlank() -> R.string.receiver_member_empty_error
                //etc..
                else -> null
            }
    }
}