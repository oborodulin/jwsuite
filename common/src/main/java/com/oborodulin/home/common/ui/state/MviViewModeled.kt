package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MviViewModeled<T : Any, A : UiAction, E : UiSingleEvent> {
    val uiStateFlow: StateFlow<UiState<T>>

    val errorMessage: StateFlow<String>

    val actionsJobFlow: SharedFlow<Job?>
    val singleEventFlow: Flow<E>

    // search
    val searchText: StateFlow<TextFieldValue>
    val isSearching: StateFlow<Boolean>

    // https://medium.com/geekculture/add-remove-in-lazycolumn-list-aka-recyclerview-jetpack-compose-7c4a2464fc9f
    /*fun deleteItem
    fun addItem*/

    fun onSearchTextChange(text: TextFieldValue)
    fun singleSelectItem(selectedItem: ListItemModel)

    //fun checkItem(checkedItem: ListItemModel, checkValue: Boolean)
    fun submitAction(action: A): Job?
}