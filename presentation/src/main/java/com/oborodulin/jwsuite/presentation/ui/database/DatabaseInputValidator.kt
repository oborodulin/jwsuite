package com.oborodulin.jwsuite.presentation.ui.database

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Presentation.DatabaseInputValidator"

sealed class DatabaseInputValidator : Validatable {
    data object DatabaseBackupPeriod : DatabaseInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.territory_processing_period_empty_error
                //etc..
                else -> null
            }
    }
}
