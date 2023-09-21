package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.RoomUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface RoomViewModel : DialogViewModeled<RoomUi, RoomUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val street: StateFlow<InputListItemWrapper<StreetsListItem>>
    val house: StateFlow<InputListItemWrapper<ListItemModel>>
    val entrance: StateFlow<InputListItemWrapper<ListItemModel>>
    val floor: StateFlow<InputListItemWrapper<ListItemModel>>
    val territory: StateFlow<InputListItemWrapper<ListItemModel>>
    val roomNum: StateFlow<InputWrapper>
    val isIntercom: StateFlow<InputWrapper>
    val isResidential: StateFlow<InputWrapper>
    val isForeignLanguage: StateFlow<InputWrapper>
    val roomDesc: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: RoomFields, isFocused: Boolean)
    fun moveFocusImeAction()
}