package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class GroupsListUiSingleEvent : UiSingleEvent {
    data class OpenGroupScreen(val navRoute: String) : GroupsListUiSingleEvent()
}

