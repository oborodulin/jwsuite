package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation.R

private const val TAG = "Presentation.AppSettingInputValidator"

sealed class AppSettingInputValidator : Validatable {
    data object DatabaseBackupPeriod : AppSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.database_backup_period_empty_error
                //etc..
                else -> null
            }
    }
}
