package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.RoadType
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StreetViewModel : DialogViewModeled<StreetUi, StreetUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val roadTypes: StateFlow<MutableMap<RoadType, String>>

    val locality: StateFlow<InputListItemWrapper<ListItemModel>>
    //val localityDistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    //val microdistrict: StateFlow<InputListItemWrapper<ListItemModel>>
    val roadType: StateFlow<InputWrapper>
    val isPrivateSector: StateFlow<InputWrapper>
    val estimatedHouses: StateFlow<InputWrapper>
    val streetName: StateFlow<InputWrapper>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: StreetFields, isFocused: Boolean)
    fun moveFocusImeAction()
}