package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CongregationViewModel :
    DialogViewModeled<CongregationUi, CongregationUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val congregationNum: StateFlow<InputWrapper>
    val congregationName: StateFlow<InputWrapper>
    val territoryMark: StateFlow<InputWrapper>
    val isFavorite: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: CongregationFields, isFocused: Boolean)
    fun moveFocusImeAction()
}