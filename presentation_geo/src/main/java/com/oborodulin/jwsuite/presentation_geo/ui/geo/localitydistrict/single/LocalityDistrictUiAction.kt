package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class LocalityDistrictUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data class Load(val localityDistrictId: UUID? = null) : LocalityDistrictUiAction()
    data object Save : LocalityDistrictUiAction()
}