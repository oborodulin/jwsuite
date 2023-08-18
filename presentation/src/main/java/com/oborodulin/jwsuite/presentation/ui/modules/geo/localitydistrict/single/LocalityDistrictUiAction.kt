package com.oborodulin.jwsuite.presentation.ui.modules.geo.localitydistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityDistrictUiAction : UiAction {
    data class Load(val regionDistrictId: UUID? = null) : LocalityDistrictUiAction()
    object Save : LocalityDistrictUiAction()
}