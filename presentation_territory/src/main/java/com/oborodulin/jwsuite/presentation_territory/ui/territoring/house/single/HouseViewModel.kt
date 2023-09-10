package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface HouseViewModel :
    DialogViewModeled<TerritoryStreetUiModel, HouseUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>
    val isPrivateSector: StateFlow<InputWrapper>
    val isEvenSide: StateFlow<InputWrapper>
    val estimatedHouses: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: HouseFields, isFocused: Boolean)
    fun moveFocusImeAction()
}