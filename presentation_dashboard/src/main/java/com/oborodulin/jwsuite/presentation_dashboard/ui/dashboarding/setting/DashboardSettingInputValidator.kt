package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

import com.oborodulin.home.common.ui.components.field.util.Validatable
import com.oborodulin.jwsuite.presentation_dashboard.R

private const val TAG = "Dashboarding.AppSettingInputValidator"

sealed class DashboardSettingInputValidator : Validatable {
    data object DatabaseBackupPeriod : DashboardSettingInputValidator() {
        override fun errorIdOrNull(vararg inputs: String?): Int? =
            when {
                inputs[0].isNullOrEmpty() -> R.string.database_backup_period_empty_error
                //etc..
                else -> null
            }
    }
}
