package com.oborodulin.jwsuite.presentation.ui.modules.geo.microdistrict.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.VillageType
import com.oborodulin.jwsuite.presentation.ui.modules.geo.model.MicrodistrictUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MicrodistrictViewModel :
    DialogViewModeled<MicrodistrictUi, MicrodistrictUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>
    val microdistrictTypes: StateFlow<MutableMap<VillageType, String>>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrictShortName: StateFlow<InputWrapper>
    val microdistrictType: StateFlow<InputWrapper>
    val microdistrictName: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: MicrodistrictFields, isFocused: Boolean)
    fun moveFocusImeAction()
}