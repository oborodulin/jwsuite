package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class RegionDistrictsListUiAction : UiAction {
    object Load : RegionDistrictsListUiAction()
    data class EditPayer(val payerId: UUID) : RegionDistrictsListUiAction()
    data class DeletePayer(val payerId: UUID) : RegionDistrictsListUiAction()
}