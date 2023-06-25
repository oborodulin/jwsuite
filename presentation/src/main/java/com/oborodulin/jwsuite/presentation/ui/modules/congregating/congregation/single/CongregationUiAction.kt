package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.common.ui.state.UiAction
import java.util.*

sealed class CongregationUiAction : UiAction {
    object Create : CongregationUiAction()
    data class Load(val payerId: UUID) : CongregationUiAction()
    object Save : CongregationUiAction()
//    data class ShowCompletedTasks(val show: Boolean) : PayersListEvent()
//    data class ChangeSortByPriority(val enable: Boolean) : PayersListEvent()
//    data class ChangeSortByDeadline(val enable: Boolean) : PayersListEvent()
}