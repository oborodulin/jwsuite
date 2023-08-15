package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import androidx.compose.ui.text.input.TextFieldValue
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TerritoriesGridViewModel :
    DialogViewModeled<List<TerritoriesListItem>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>

    // search
    val handOutSearchText: StateFlow<TextFieldValue>
    val atWorkSearchText: StateFlow<TextFieldValue>
    val idleSearchText: StateFlow<TextFieldValue>
     fun onHandOutSearchTextChange(text: TextFieldValue)
    fun onAtWorkSearchTextChange(text: TextFieldValue)
    fun onIdleSearchTextChange(text: TextFieldValue)

    val member: StateFlow<InputListItemWrapper<ListItemModel>>
    val receivingDate: StateFlow<InputWrapper>
    val checkedTerritories: StateFlow<List<TerritoriesListItem>>

    val areTerritoriesChecked: StateFlow<Boolean>
    val areHandOutInputsValid: StateFlow<Boolean>

    fun observeCheckedTerritories()
    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoriesFields, isFocused: Boolean)
    fun moveFocusImeAction()
}