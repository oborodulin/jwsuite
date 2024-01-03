package com.oborodulin.jwsuite.presentation_territory.ui.reporting.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MemberReportsListUiSingleEvent : UiSingleEvent {
    data class OpenMemberReportScreen(val navRoute: String) : MemberReportsListUiSingleEvent()
}

