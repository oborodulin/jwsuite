package com.oborodulin.jwsuite.presentation_geo.ui.geo

import com.oborodulin.home.common.ui.state.UiAction

sealed class GeoUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Init : GeoUiAction()
    //data class Load(val payerId: UUID) : AccountingUiAction()
}

