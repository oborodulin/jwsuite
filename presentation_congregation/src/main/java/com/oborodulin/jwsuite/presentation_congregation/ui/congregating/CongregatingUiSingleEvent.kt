package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class CongregatingUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : CongregatingUiSingleEvent()
}

