package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import com.oborodulin.home.common.ui.state.UiAction

sealed class DashboardingUiAction : UiAction {
    object Init : DashboardingUiAction()
}

