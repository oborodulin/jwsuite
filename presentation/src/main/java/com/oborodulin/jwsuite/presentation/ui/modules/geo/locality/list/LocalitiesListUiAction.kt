package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class LocalitiesListUiAction : UiAction {
    object Load : LocalitiesListUiAction()
    data class EditPayer(val payerId: UUID) : LocalitiesListUiAction()
    data class DeletePayer(val payerId: UUID) : LocalitiesListUiAction()
    data class FavoritePayer(val payerId: UUID) : LocalitiesListUiAction()
//    data class ShowCompletedTasks(val show: Boolean) : PayersListEvent()
//    data class ChangeSortByPriority(val enable: Boolean) : PayersListEvent()
//    data class ChangeSortByDeadline(val enable: Boolean) : PayersListEvent()
}