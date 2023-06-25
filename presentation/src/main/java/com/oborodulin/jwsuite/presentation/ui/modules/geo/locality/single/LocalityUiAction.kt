package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityUiAction : UiAction {
    object Create : LocalityUiAction()
    data class Load(val localityId: UUID) : LocalityUiAction()
    object Save : LocalityUiAction()
}