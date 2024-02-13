package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

import com.oborodulin.home.common.ui.state.UiAction

sealed class DashboardSettingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : DashboardSettingUiAction()
    data object Save : DashboardSettingUiAction()
}