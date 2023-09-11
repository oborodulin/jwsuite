package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryStreetUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TerritoryHouseViewModel :
    DialogViewModeled<TerritoryStreetUiModel, TerritoryHouseUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val house: StateFlow<InputListItemWrapper<ListItemModel>>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoryHouseFields, isFocused: Boolean)
    fun moveFocusImeAction()
}