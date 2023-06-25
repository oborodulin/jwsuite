package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class RegionsListUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : RegionsListUiSingleEvent()
}

