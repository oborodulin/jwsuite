package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardSettingsUiModel
import kotlinx.coroutines.flow.StateFlow

interface DashboardSettingViewModel :
    DialogViewModeled<DashboardSettingsUiModel, DashboardSettingUiAction, UiSingleEvent, DashboardSettingFields> {
    val databaseBackupPeriod: StateFlow<InputWrapper>
}