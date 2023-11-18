package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.state.UiAction

sealed class AppSettingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : AppSettingUiAction()
    data object Save : AppSettingUiAction()
}