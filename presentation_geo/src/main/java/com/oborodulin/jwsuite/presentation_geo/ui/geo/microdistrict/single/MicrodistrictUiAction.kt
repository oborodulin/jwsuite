package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MicrodistrictUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val microdistrictId: UUID? = null) : MicrodistrictUiAction()
    data object Save : MicrodistrictUiAction()
}