package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.components.field.util.Focusable
import com.oborodulin.home.common.ui.model.ListItemModel

interface CheckedListDialogViewModeled<T : Any, A : UiAction, E : UiSingleEvent, F : Focusable, CT : List<ListItemModel>> :
    DialogViewModeled<T, A, E, F>, CheckedListViewModeled<CT>