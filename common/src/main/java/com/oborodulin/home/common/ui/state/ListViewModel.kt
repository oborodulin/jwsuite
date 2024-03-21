package com.oborodulin.home.common.ui.state

import androidx.lifecycle.viewModelScope
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.LogLevel.LOG_MVI_LIST
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

private const val TAG = "Common.ListViewModel"

abstract class ListViewModel<T : List<ListItemModel>, S : UiState<T>, A : UiAction, E : UiSingleEvent> :
    MviViewModel<T, S, A, E>(), ListViewModeled<T, A, E> {
    private val _selectedItem: MutableStateFlow<ListItemModel?> = MutableStateFlow(null)
    override val selectedItemFlow = _selectedItem.asStateFlow()

    override val areSingleSelected = _selectedItem.map { item -> item != null }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    // https://www.youtube.com/watch?v=CfL6Dl2_dAE
    // https://stackoverflow.com/questions/70709121/how-to-convert-a-flowcustomtype-to-stateflowuistate-android-kotlin
    /*    @OptIn(FlowPreview::class)
        override val uiStateFlow: StateFlow<S> = searchText
            .debounce(500L)
            .onEach { _isSearching.update { true } }
            .combine(_uiStateFlow) { query, uiState ->
                if (query.text.isBlank()) uiState
                if (uiState !is UiState.Success<*>) uiState
                uiState as UiState.Success<*>
                if (uiState.data !is List<*>) uiState
                uiState.data as List<*>
                if (uiState.data.first() !is Searchable) uiState
                UiState.Success(data = uiState.data.filter {
                    (it as Searchable).doesMatchSearchQuery(query.text)
                }) as S

            }
            .onEach { _isSearching.update { false } }
            .stateIn(
                viewModelScope, SharingStarted.WhileSubscribed(5000),
                _uiStateFlow.value
            )
    */

    // https://medium.com/@wunder.saqib/compose-single-selection-with-data-binding-37a12cf51bc8
    override fun singleSelectItem(selectedItem: ListItemModel) {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("singleSelectItem(...) called")
        }
        uiState()?.let { uiState ->
            uiState.firstOrNull { it.selected }?.let { it.selected = false }
            _selectedItem.value =
                uiState.find { it.itemId == selectedItem.itemId }?.apply { selected = true }
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d("selected %s list item", _selectedItem.value)
            }
        }
    }

    override fun singleSelectedItem(): StateFlow<ListItemModel?> {
        if (LOG_MVI_LIST) {
            Timber.tag(TAG).d("singleSelectedItem() called")
        }
        uiState()?.let { uiState ->
            val item =
                uiState.getOrNull(uiState.indexOfFirst { it.selected }.takeIf { it >= 0 } ?: 0)
            if (item != _selectedItem.value) _selectedItem.value = item
            if (LOG_MVI_LIST) {
                Timber.tag(TAG).d("selected %s list item", item)
            }
        }

        return selectedItemFlow
    }

    /*override fun checkItem(checkedItem: ListItemModel, checkValue: Boolean) {
        if (LOG_MVI_LIST) {Timber.tag(TAG).d("observeSelection() called")}
        uiState()?.let { uiState ->
            if (uiState is List<*>) {
                ((uiState as List<*>).find {
                    (it is ListItemModel) && it.itemId == checkedItem.itemId
                } as? ListItemModel)?.checked = checkValue
                if (LOG_MVI_LIST) {Timber.tag(TAG).d("checked %s list item", checkedItem)}
            }
        }
    }*/
}