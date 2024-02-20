package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.datamanagement

import com.oborodulin.home.common.ui.state.UiAction

sealed class DataManagementUiAction(override val isEmitJob: Boolean = true) : UiAction {
    data object Load : DataManagementUiAction()
    data object Save : DataManagementUiAction()
}