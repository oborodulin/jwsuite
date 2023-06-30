package com.oborodulin.home.common.ui.state

import kotlinx.coroutines.flow.StateFlow

interface MviViewModelated<T : Any> {
    val uiStateFlow: StateFlow<UiState<T>>
}