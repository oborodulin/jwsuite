package com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.MviViewModelated
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface LocalityViewModel : MviViewModelated<LocalityUi> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>

    val region: StateFlow<InputListItemWrapper>
    val regionDistrict: StateFlow<InputListItemWrapper>
    val localityCode: StateFlow<InputWrapper>
    val localityShortName: StateFlow<InputWrapper>
    val localityType: StateFlow<InputWrapper>
    val localityName: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: LocalityFields, isFocused: Boolean)
    fun moveFocusImeAction()
    fun onContinueClick(onSuccess: () -> Unit)
}