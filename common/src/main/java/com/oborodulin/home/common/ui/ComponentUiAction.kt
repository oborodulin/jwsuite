package com.oborodulin.home.common.ui

import com.oborodulin.home.common.ui.state.UiAction
import com.oborodulin.home.common.util.OnListItemEvent

sealed class ComponentUiAction(
    val isMenuButton: Boolean = true,
    override val isEmitJob: Boolean = true
) : UiAction {
    data class EditListItem(val event: OnListItemEvent) : ComponentUiAction()

    data class DeleteListItem(val alertText: String = "", val event: OnListItemEvent) :
        ComponentUiAction()

    data class PayListItem(val alertText: String = "", val event: OnListItemEvent) :
        ComponentUiAction(false)
}