package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.MemberUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface TerritoryViewModel : DialogViewModeled<MemberUi, TerritoryUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>
    val actionsJobFlow: SharedFlow<Job?>

    val memberTypes: StateFlow<MutableMap<MemberType, String>>

    val congregation: StateFlow<InputListItemWrapper<ListItemModel>>
    val group: StateFlow<InputListItemWrapper<ListItemModel>>
    val memberNum: StateFlow<InputWrapper>
    val memberName: StateFlow<InputWrapper>
    val surname: StateFlow<InputWrapper>
    val patronymic: StateFlow<InputWrapper>
    val pseudonym: StateFlow<InputWrapper>
    val phoneNumber: StateFlow<InputWrapper>
    val memberType: StateFlow<InputWrapper>
    val dateOfBirth: StateFlow<InputWrapper>
    val dateOfBaptism: StateFlow<InputWrapper>
    val inactiveDate: StateFlow<InputWrapper>

    val areInputsValid: StateFlow<Boolean>

    fun viewModelScope(): CoroutineScope
    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: TerritoryFields, isFocused: Boolean)
    fun moveFocusImeAction()
}