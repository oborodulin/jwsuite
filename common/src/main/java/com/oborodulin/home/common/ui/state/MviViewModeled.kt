package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModeled<T : Any, A : UiAction, E : UiSingleEvent> {
    val uiStateFlow: StateFlow<UiState<T>>

    val uiStateErrorMsg: StateFlow<String?>

    val actionsJobFlow: SharedFlow<Job?>
    val singleEventFlow: Flow<E>

    // search
    val searchText: StateFlow<TextFieldValue>
    val isSearching: StateFlow<Boolean>

    fun onSearchTextChange(text: TextFieldValue)
    fun submitAction(action: A): Job?
}