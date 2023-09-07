package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class RegionsListUiAction(override val isEmitJob: Boolean = true) : UiAction {
    object Load : RegionsListUiAction()
    data class EditRegion(val regionId: UUID) : RegionsListUiAction()
    data class DeleteRegion(val regionId: UUID) : RegionsListUiAction()
}