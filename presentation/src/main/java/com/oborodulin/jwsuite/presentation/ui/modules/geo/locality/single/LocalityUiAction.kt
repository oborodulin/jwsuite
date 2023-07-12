package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityUiAction : UiAction {
    data class Load(val localityId: UUID? = null) : LocalityUiAction()
    object Save : LocalityUiAction()
}