package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val localityId: UUID? = null) : LocalityUiAction()
    object Save : LocalityUiAction()
}