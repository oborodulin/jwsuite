package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.components.field.util.ScreenEvent
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.domain.util.MemberType
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MemberViewModel : DialogViewModeled<MemberUi, MemberUiAction, UiSingleEvent> {
    val events: Flow<ScreenEvent>

    val memberTypes: StateFlow<MutableMap<MemberType, String>>

    val congregation: StateFlow<InputListItemWrapper<CongregationsListItem>>
    val group: StateFlow<InputListItemWrapper<ListItemModel>>
    val memberNum: StateFlow<InputWrapper>
    val memberName: StateFlow<InputWrapper>
    val surname: StateFlow<InputWrapper>
    val patronymic: StateFlow<InputWrapper>
    val pseudonym: StateFlow<InputWrapper>
    val phoneNumber: StateFlow<InputWrapper>
    val dateOfBirth: StateFlow<InputWrapper>
    val dateOfBaptism: StateFlow<InputWrapper>
    val memberType: StateFlow<InputWrapper>
    val movementDate: StateFlow<InputWrapper>
    val loginExpiredDate: StateFlow<InputWrapper>

    fun getPseudonym(
        surname: String? = null, memberName: String? = null, groupNum: Int? = null,
        memberNum: String? = null
    ): String

    fun onTextFieldEntered(inputEvent: Inputable)
    fun onTextFieldFocusChanged(focusedField: MemberFields, isFocused: Boolean)
    fun moveFocusImeAction()
}