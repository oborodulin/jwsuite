package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.memberreport.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MemberReportsListUiSingleEvent : UiSingleEvent {
    data class OpenTerritoryStreetScreen(val navRoute: String) : MemberReportsListUiSingleEvent()
}

