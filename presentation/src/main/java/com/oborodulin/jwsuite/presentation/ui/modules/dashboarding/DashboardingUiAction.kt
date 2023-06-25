package com.oborodulin.jwsuite.presentation.ui.modules.dashboarding

import com.oborodulin.home.common.ui.state.UiAction

sealed class DashboardingUiAction : UiAction {
    object Init : DashboardingUiAction()
}

