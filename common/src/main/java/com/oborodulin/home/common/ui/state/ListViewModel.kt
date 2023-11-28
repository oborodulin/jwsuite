package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.model.ListItemModel
import timber.log.Timber

private const val TAG = "Common.ListViewModel"

abstract class ListViewModel<T : List<ListItemModel>, S : UiState<T>, A : UiAction, E : UiSingleEvent> :
    MviViewModel<T, S, A, E>(), ListViewModeled<T, A, E> {

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
        Timber.tag(TAG).d("singleSelectItem(...) called")
        uiState()?.let { uiState ->
            uiState.forEach { it.selected = false }
            uiState.find { it.itemId == selectedItem.itemId }?.selected = true
            Timber.tag(TAG).d("selected %s list item", selectedItem)
        }
    }

    override fun singleSelectedItem(): ListItemModel? {
        Timber.tag(TAG).d("singleSelectedItem() called")
        var selectedItem: ListItemModel? = null
        try {
            uiState()?.let { uiState ->
                selectedItem = uiState.first { it.selected }
                Timber.tag(TAG).d("selected %s list item", selectedItem)
            }
        } catch (e: NoSuchElementException) {
            Timber.tag(TAG).e(e)
        }
        return selectedItem
    }

    /*override fun checkItem(checkedItem: ListItemModel, checkValue: Boolean) {
        Timber.tag(TAG).d("observeSelection() called")
        uiState()?.let { uiState ->
            if (uiState is List<*>) {
                ((uiState as List<*>).find {
                    (it is ListItemModel) && it.itemId == checkedItem.itemId
                } as? ListItemModel)?.checked = checkValue
                Timber.tag(TAG).d("checked %s list item", checkedItem)
            }
        }
    }*/
}