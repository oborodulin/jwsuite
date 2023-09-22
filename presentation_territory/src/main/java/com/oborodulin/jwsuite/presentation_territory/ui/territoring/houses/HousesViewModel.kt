package com.oborodulin.jwsuite.presentation_territory.ui.territoring.houses

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HousesViewModel : SingleViewModeled<Any, HousesUiAction, HousesUiSingleEvent> {
    val events: Flow<ScreenEvent>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: HousesFields, isFocused: Boolean)
    fun moveFocusImeAction()
}