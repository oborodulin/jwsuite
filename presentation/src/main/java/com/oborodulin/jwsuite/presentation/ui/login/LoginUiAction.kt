package com.oborodulin.jwsuite.presentation.ui.login

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LoginUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val regionId: UUID? = null) : LoginUiAction()
    object Save : LoginUiAction()
}