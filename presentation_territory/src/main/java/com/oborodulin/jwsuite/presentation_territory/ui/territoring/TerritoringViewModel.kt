package com.oborodulin.jwsuite.presentation_territory.ui.territoring

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoringUi
import com.oborodulin.jwsuite.presentation_territory.ui.model.TerritoryLocationsListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TerritoringViewModel :
    SingleViewModeled<TerritoringUi, TerritoringUiAction, TerritoringUiSingleEvent> {
    val events: Flow<ScreenEvent>

    val isPrivateSector: StateFlow<InputWrapper>
    val location: StateFlow<InputListItemWrapper<TerritoryLocationsListItem>>

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoringFields, isFocused: Boolean)
    fun moveFocusImeAction()
}