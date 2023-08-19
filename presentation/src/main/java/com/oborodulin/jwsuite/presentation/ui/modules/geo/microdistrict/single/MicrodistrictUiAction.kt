package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class MicrodistrictUiAction : UiAction {
    data class Load(val microdistrictId: UUID? = null) : MicrodistrictUiAction()
    data object Save : MicrodistrictUiAction()
}