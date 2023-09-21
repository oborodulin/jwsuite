package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class RoomsListUiSingleEvent : UiSingleEvent {
    data class OpenRoomScreen(val navRoute: String) : RoomsListUiSingleEvent()
    data class OpenTerritoryRoomScreen(val navRoute: String) : RoomsListUiSingleEvent()
}

