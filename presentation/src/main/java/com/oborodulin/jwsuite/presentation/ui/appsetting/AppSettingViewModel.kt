package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.model.AppSettingsUiModel
import kotlinx.coroutines.flow.StateFlow

interface AppSettingViewModel :
    DialogViewModeled<AppSettingsUiModel, AppSettingUiAction, UiSingleEvent, AppSettingFields> {
    val databaseBackupPeriod: StateFlow<InputWrapper>
}