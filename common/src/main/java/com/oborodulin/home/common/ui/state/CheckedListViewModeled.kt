package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.StateFlow

interface CheckedListViewModeled<T : List<ListItemModel>> {
    val checkedListItems: StateFlow<T>
    fun observeCheckedListItems()
}