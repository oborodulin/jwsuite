package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.components.field.util.InputWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.DialogViewModeled
import com.oborodulin.home.common.ui.state.UiSingleEvent
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MemberRoleUi
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import kotlinx.coroutines.flow.StateFlow

interface MemberRoleViewModel :
    DialogViewModeled<MemberRoleUi, MemberRoleUiAction, UiSingleEvent, MemberRoleFields> {
    val member: StateFlow<InputListItemWrapper<ListItemModel>>
    val role: StateFlow<InputListItemWrapper<ListItemModel>>
    val roleExpiredDate: StateFlow<InputWrapper>
}