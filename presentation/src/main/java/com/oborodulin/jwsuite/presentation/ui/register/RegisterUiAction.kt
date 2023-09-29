package com.oborodulin.jwsuite.presentation.ui.register

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class RegisterUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val regionId: UUID? = null) : RegisterUiAction()
    data object Register : RegisterUiAction()
}