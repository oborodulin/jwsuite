package com.oborodulin.home.common.ui.state

import androidx.compose.ui.text.input.TextFieldValue
import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.StateFlow

interface ListViewModeled<T : List<ListItemModel>, A : UiAction, E : UiSingleEvent> :
    MviViewModeled<T, A, E> {
    // https://medium.com/geekculture/add-remove-in-lazycolumn-list-aka-recyclerview-jetpack-compose-7c4a2464fc9f
    /*fun deleteItem
    fun addItem*/

    fun singleSelectItem(selectedItem: ListItemModel)
    //fun checkItem(checkedItem: ListItemModel, checkValue: Boolean)
}