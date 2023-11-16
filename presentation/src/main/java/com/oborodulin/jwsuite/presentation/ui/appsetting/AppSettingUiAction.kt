package com.oborodulin.jwsuite.presentation.ui.appsetting

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class AppSettingUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val memberId: UUID? = null) : AppSettingUiAction()
    data object Save : AppSettingUiAction()
}