package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class RegionDistrictsListUiAction : UiAction {
    object Load : RegionDistrictsListUiAction()
    data class EditPayer(val payerId: UUID) : RegionDistrictsListUiAction()
    data class DeletePayer(val payerId: UUID) : RegionDistrictsListUiAction()
    data class FavoritePayer(val payerId: UUID) : RegionDistrictsListUiAction()
//    data class ShowCompletedTasks(val show: Boolean) : PayersListEvent()
//    data class ChangeSortByPriority(val enable: Boolean) : PayersListEvent()
//    data class ChangeSortByDeadline(val enable: Boolean) : PayersListEvent()
}