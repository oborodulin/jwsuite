package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.RegionDistrictUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RegionDistrictViewModel :
    DialogViewModeled<RegionDistrictUi, RegionDistrictUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val region: StateFlow<InputListItemWrapper<ListItemModel>>
    val districtShortName: StateFlow<InputWrapper>
    val districtName: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: RegionDistrictFields, isFocused: Boolean)
    fun moveFocusImeAction()
}