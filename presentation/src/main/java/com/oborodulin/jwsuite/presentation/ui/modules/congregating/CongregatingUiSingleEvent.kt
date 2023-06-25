package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class CongregatingUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : CongregatingUiSingleEvent()
}

