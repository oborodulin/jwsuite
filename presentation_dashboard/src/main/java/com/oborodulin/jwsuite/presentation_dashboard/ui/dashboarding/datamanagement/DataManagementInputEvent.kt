package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class DataManagementInputEvent(val value: String) : Inputable {
    data class DatabaseBackupPeriod(val input: String) : DataManagementInputEvent(input)

    override fun value() = this.value
}
