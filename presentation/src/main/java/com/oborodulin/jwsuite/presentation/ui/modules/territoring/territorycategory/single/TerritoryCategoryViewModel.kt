package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory.single

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoryCategoryUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TerritoryCategoryViewModel :
    DialogViewModeled<TerritoryCategoryUi, TerritoryCategoryUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val territoryCategoryCode: StateFlow<InputWrapper>
    val territoryCategoryMark: StateFlow<InputWrapper>
    val territoryCategoryName: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoryCategoryFields, isFocused: Boolean)
    fun moveFocusImeAction()
}