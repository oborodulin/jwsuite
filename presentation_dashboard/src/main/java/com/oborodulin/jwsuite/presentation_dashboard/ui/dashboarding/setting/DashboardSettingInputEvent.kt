package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class DashboardSettingInputEvent(val value: String) : Inputable {
    data class DatabaseBackupPeriod(val input: String) : DashboardSettingInputEvent(input)

    override fun value() = this.value
}
