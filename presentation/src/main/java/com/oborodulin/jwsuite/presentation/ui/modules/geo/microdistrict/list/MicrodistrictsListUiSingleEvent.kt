package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class MicrodistrictsListUiSingleEvent : UiSingleEvent {
    data class OpenMicrodistrictScreen(val navRoute: String) : MicrodistrictsListUiSingleEvent()
}

