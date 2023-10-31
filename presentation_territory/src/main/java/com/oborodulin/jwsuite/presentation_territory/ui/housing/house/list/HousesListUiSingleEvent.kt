package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class HousesListUiSingleEvent : UiSingleEvent {
    data class OpenHouseScreen(val navRoute: String) : HousesListUiSingleEvent()
}

