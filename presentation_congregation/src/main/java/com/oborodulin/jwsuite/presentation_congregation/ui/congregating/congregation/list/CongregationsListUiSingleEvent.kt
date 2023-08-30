package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class CongregationsListUiSingleEvent : UiSingleEvent {
    data class OpenCongregationScreen(val navRoute: String) : CongregationsListUiSingleEvent()
}

