package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class GroupUiAction : UiAction {
    data class Load(val regionId: UUID? = null) : GroupUiAction()
    object Save : GroupUiAction()
}