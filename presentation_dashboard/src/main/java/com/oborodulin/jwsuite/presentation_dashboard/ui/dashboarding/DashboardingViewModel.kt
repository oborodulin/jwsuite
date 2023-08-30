package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding

import com.oborodulin.home.common.ui.state.UiState
import com.oborodulin.jwsuite.presentation_dashboard.ui.model.DashboardingUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DashboardingViewModel {
    val uiStateFlow: StateFlow<UiState<DashboardingUi>>
    val singleEventFlow : Flow<DashboardingUiSingleEvent>

    fun submitAction(action: DashboardingUiAction): Job?
}