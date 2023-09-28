package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.MicrodistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetMicrodistrictsUiModel
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StreetMicrodistrictViewModel :
    DialogViewModeled<StreetMicrodistrictsUiModel, StreetMicrodistrictUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
    val checkedListItems: StateFlow<List<MicrodistrictsListItem>>

    val areInputsValid: StateFlow<Boolean>

    fun observeCheckedListItems()
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: StreetMicrodistrictFields, isFocused: Boolean)
    fun moveFocusImeAction()
}