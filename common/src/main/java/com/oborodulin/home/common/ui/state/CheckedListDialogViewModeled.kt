package com.oborodulin.home.common.ui.state

import com.oborodulin.home.common.ui.components.field.util.Focusable

interface CheckedListDialogViewModeled<T : Any, A : UiAction, E : UiSingleEvent, F : Focusable> :
    DialogViewModeled<T, A, E, F>, CheckedListViewModeled