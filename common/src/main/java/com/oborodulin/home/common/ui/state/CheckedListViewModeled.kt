package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.model.ListItemModel
import kotlinx.coroutines.flow.StateFlow

interface CheckedListViewModeled {
    val checkedListItems: StateFlow<List<ListItemModel>>
    fun observeCheckedListItems()
}