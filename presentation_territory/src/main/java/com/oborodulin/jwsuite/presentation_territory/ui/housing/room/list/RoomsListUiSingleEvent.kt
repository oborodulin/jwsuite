package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class RoomsListUiSingleEvent : UiSingleEvent {
    data class OpenRoomScreen(val navRoute: String) : RoomsListUiSingleEvent()
}

