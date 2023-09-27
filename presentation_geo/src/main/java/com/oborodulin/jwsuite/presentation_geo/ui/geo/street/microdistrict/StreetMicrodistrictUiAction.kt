package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class StreetMicrodistrictUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val streetId: UUID) : StreetMicrodistrictUiAction()
    data object Save : StreetMicrodistrictUiAction()
}