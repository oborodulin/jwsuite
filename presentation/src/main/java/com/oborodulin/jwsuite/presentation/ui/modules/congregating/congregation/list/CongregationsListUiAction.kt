package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import com.oborodulin.home.common.ui.state.UiAction
import java.util.UUID

sealed class CongregationsListUiAction : UiAction {
    object Load : CongregationsListUiAction()
    data class EditCongregation(val congregationId: UUID) : CongregationsListUiAction()
    data class DeleteCongregation(val congregationId: UUID) : CongregationsListUiAction()
    data class FavoriteCongregation(val congregationId: UUID) : CongregationsListUiAction()
//    data class ShowCompletedTasks(val show: Boolean) : PayersListEvent()
//    data class ChangeSortByPriority(val enable: Boolean) : PayersListEvent()
//    data class ChangeSortByDeadline(val enable: Boolean) : PayersListEvent()
}