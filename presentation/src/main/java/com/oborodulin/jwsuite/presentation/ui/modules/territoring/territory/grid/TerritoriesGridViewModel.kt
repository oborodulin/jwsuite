package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SingleViewModeled
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.TerritoringFields
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.model.TerritoriesListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TerritoriesGridViewModel :
    SingleViewModeled<List<TerritoriesListItem>, TerritoriesGridUiAction, TerritoriesGridUiSingleEvent> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>

    val member: StateFlow<InputListItemWrapper<ListItemModel>>
    val receivingDate: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun handleActionJob(action: () -> Unit, afterAction: () -> Unit)
    fun onTextFieldEntered(inputEvent: Inputable)
    fun moveFocusImeAction()
}