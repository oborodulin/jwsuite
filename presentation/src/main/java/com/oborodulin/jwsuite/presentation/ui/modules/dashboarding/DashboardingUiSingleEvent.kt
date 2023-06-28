package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class DashboardingUiSingleEvent : UiSingleEvent {
    data class OpenCongregationScreen(val navRoute: String) : DashboardingUiSingleEvent()
}

