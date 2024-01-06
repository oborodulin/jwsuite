package com.oborodulin.jwsuite.presentation_territory.ui.reporting.rooms

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class ReportRoomsUiSingleEvent : UiSingleEvent {
    data class OpenMemberReportScreen(val navRoute: String) : ReportRoomsUiSingleEvent()
}

