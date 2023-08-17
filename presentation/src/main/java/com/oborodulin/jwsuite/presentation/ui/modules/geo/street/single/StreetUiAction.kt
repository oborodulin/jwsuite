package com.oborodulin.jwsuite.presentation.ui.modules.geo.street.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class StreetUiAction : UiAction {
    data class Load(val streetId: UUID? = null) : StreetUiAction()
    data object Save : StreetUiAction()
}