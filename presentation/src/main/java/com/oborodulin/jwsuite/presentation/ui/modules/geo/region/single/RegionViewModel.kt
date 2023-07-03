package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation.ui.model.LocalityUi
import com.oborodulin.jwsuite.presentation.ui.model.RegionUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface RegionViewModel : SingleViewModeled<RegionUi, RegionUiAction> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>

    val regionCode: StateFlow<InputWrapper>
    val regionName: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: RegionFields, isFocused: Boolean)
    fun moveFocusImeAction()
}