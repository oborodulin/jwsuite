package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.LocalityDistrictsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetLocalityDistrictUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StreetLocalityDistrictViewModel :
    DialogViewModeled<StreetLocalityDistrictUiModel, StreetLocalityDistrictUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val street: StateFlow<InputListItemWrapper<ListItemModel>>
    val checkedListItems: StateFlow<List<LocalityDistrictsListItem>>

    val areInputsValid: StateFlow<Boolean>

    fun observeCheckedListItems()
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: StreetLocalityDistrictFields, isFocused: Boolean)
    fun moveFocusImeAction()
}