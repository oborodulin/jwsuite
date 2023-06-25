package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class CongregationsListUiSingleEvent : UiSingleEvent {
    data class OpenCongregationScreen(val navRoute: String) : CongregationsListUiSingleEvent()
}

