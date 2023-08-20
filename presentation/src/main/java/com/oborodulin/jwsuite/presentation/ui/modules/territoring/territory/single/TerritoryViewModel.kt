package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TerritoryViewModel : DialogViewModeled<TerritoryUi, TerritoryUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val congregation: StateFlow<InputListItemWrapper<ListItemModel>>
    val category: StateFlow<InputListItemWrapper<ListItemModel>>
    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val territoryNum: StateFlow<InputWrapper>
    val isPrivateSector: StateFlow<InputWrapper>
    val isBusiness: StateFlow<InputWrapper>
    val isGroupMinistry: StateFlow<InputWrapper>
    val isActive: StateFlow<InputWrapper>
    val territoryDesc: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoryFields, isFocused: Boolean)
    fun moveFocusImeAction()
}