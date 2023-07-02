package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.flow.StateFlow

interface MviViewModeled<T : Any> {
    val uiStateFlow: StateFlow<UiState<T>>
}