package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list

import com.oborodulin.home.common.ui.state.UiSingleEvent

sealed class LocalitiesListUiSingleEvent : UiSingleEvent {
    data class OpenPayerScreen(val navRoute: String) : LocalitiesListUiSingleEvent()
}

