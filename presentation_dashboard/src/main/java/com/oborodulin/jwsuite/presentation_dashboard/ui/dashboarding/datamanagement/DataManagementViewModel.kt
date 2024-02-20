package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardSettingsUiModel
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DataManagementSettingsUiModel
import kotlinx.coroutines.flow.StateFlow

interface DataManagementViewModel :
    DialogViewModeled<DataManagementSettingsUiModel, DataManagementUiAction, UiSingleEvent, DataManagementFields> {
    val databaseBackupPeriod: StateFlow<InputWrapper>
}