package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorystreet.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryStreetUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TerritoryStreetViewModel :
    DialogViewModeled<TerritoryStreetUi, TerritoryStreetUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<ListItemModel>>
    val isPrivateSector: StateFlow<InputWrapper>
    val isEvenSide: StateFlow<InputWrapper>
    val estimatedHouses: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoryStreetFields, isFocused: Boolean)
    fun moveFocusImeAction()
}